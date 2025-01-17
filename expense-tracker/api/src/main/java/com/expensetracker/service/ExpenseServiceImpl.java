package com.expensetracker.service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.expensetracker.dto.DailyExpenseItem;
import com.expensetracker.dto.DailyReport;
import com.expensetracker.dto.ExpenseCreateDto;
import com.expensetracker.dto.ExpenseForUpdate;
import com.expensetracker.dto.ExpenseItem;
import com.expensetracker.dto.ExpenseUpdateDto;
import com.expensetracker.dto.ReportResponse;
import com.expensetracker.entities.Account;
import com.expensetracker.entities.Expense;
import com.expensetracker.error.AccountNotFoundException;
import com.expensetracker.repository.AccountRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.util.WebSocket;

@Service
@Transactional(readOnly = true)
public class ExpenseServiceImpl implements ExpenseService{

	private final ExpenseRepository expenseRepo;
	private final AccountRepository accountRepo;
	private final WebSocket webSocket;
	
	public ExpenseServiceImpl(ExpenseRepository expenseRepo, AccountRepository accountRepo, WebSocket webSocket) {
		this.expenseRepo = expenseRepo;
		this.accountRepo = accountRepo;
		this.webSocket = webSocket;
	}
	
	@Transactional
	@Override
	public void add(ExpenseCreateDto inputs) {
		Expense expense = new Expense();
		expense.setAmount(inputs.getAmount());
		expense.setCategory(inputs.getCategory());
		expense.setDescription(inputs.getDescription());
		expense.setAccount(getAccount(getAccountId()));
		expense.setDate(LocalDate.now());
		expenseRepo.save(expense);
		notifyIfExpenseLimitExceeded(getAccountId());
	}
	
	private double getMonthTotalExpenses() {
		return getThisMonthReport().getPeriodTotal();
	}
	
	public void notifyIfExpenseLimitExceeded(Long accountId) {
		Account account = getAccount(accountId);
		double totalSpentThisMonth = getMonthTotalExpenses();
		double limit = account.getExpenseLimit();
		if (limit > 0) {
		double remainingAmount = limit - totalSpentThisMonth;
		 if ( remainingAmount <= (0.2 * limit ) && remainingAmount >= 0) {
			 System.out.print("ExpenseServiceImpl, limit was: " + limit);
	            webSocket.sendExpenseLimitNotification(account.getFirstName(), totalSpentThisMonth, limit, remainingAmount);
	        }  else if (remainingAmount < 0) {
	            System.out.println("ExpenseServiceImpl, limit exceeded by: " + (-remainingAmount));
	            webSocket.sendExpenseOverLimitNotification(account.getFirstName(), totalSpentThisMonth, limit);
	        }
	}
	}

	@Override
	public List<ExpenseItem> getAllByAccount(Pageable pageable) {
		return expenseRepo.findByAccountId(getAccountId(), pageable);
	}
	
	Long getAccountId() {
		Authentication authentication = SecurityContextHolder
			.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentAccountId = authentication
			    .getName();
		    return Long.parseLong(currentAccountId);
		}
		return null;
	    }

	Account getAccount(Long accountId) {
    	return accountRepo.findById(accountId)
    		.orElseThrow(
    			() -> new AccountNotFoundException(
    				"Account not found"));
    }

	@Transactional
	@Override
	public void delete(Long id) {
		Expense expense = expenseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        expenseRepo.delete(expense);
		
	}

	@Override
	public ExpenseForUpdate getForUpdate(Long id) {
		return expenseRepo.findProjectedById(id);
	}
	
	@Transactional
	@Override
	public void update(Long id, ExpenseUpdateDto inputs) {
		Expense expense = expenseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
		Optional.ofNullable(inputs.getAmount()).ifPresent(expense::setAmount);
	    Optional.ofNullable(inputs.getCategory()).ifPresent(expense::setCategory);
	    Optional.ofNullable(inputs.getDescription()).ifPresent(expense::setDescription);
	    notifyIfExpenseLimitExceeded(getAccountId());
		
	}
	
	@Override
	public DailyReport getMostRecentReport() {
	   
	    List<Object[]> results = expenseRepo.findMostRecentExpenses(getAccountId());
	    
	    if (results.isEmpty()) {
	        return null; // TODO
	    }
	    
	    List<DailyExpenseItem> expenses = results.stream()
	            .map(row -> createExpenseItem(row))
	            .toList();
	    
	    LocalDate date = (LocalDate) results.get(0)[0];
	    String formattedDate = formatDate(date);

	    
	    DailyReport report =  new DailyReport();
	    double dailyTotal = expenses.stream().mapToDouble(DailyExpenseItem::getAmount).sum();
	    report.setDailyTotal(dailyTotal);
	    report.setFormattedDate(formattedDate);
	    report.setExpenses(expenses);
	    return report;
	}

	
	 public ReportResponse getThisWeekReport() {
		 return generateReport(getStartOfWeek(), getEndOfWeek() );
	    }

	    public ReportResponse getThisMonthReport() {
	    	return generateReport(getStartOfMonth(), getEndOfMonth() );
	    }

	    public ReportResponse getThisYearReport() {
	       return generateReport(getStartOfYear(), getEndOfYear() );
	    }
	
	    ReportResponse generateReport(LocalDate startDate, LocalDate endDate ) {
	    	
	    	List<ExpenseItem> expenseItems = expenseRepo.findExpenseItemsByDateRange(startDate, endDate, getAccountId());
	    	
	    	double periodTotal = expenseItems.stream().collect(Collectors.summingDouble(ExpenseItem::getAmount));

	    	Map<String, Double> categoryTotals = expenseItems.stream()
	        	    .collect(Collectors.groupingBy(ExpenseItem::getCategory, 
	        	        Collectors.summingDouble(ExpenseItem::getAmount))); 
	    	
	    	return new ReportResponse(startDate, endDate, categoryTotals, periodTotal);
	    }
	    
	    private LocalDate getStartOfWeek() {
	        return LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
	    }

	    private LocalDate getEndOfWeek() {
	        return getStartOfWeek().plusDays(6);
	    }

	    private LocalDate getStartOfMonth() {
	        return LocalDate.now().withDayOfMonth(1);
	    }

	    private LocalDate getEndOfMonth() {
	        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
	    }

	    private LocalDate getStartOfYear() {
	        return LocalDate.now().withDayOfYear(1);
	    }

	    private LocalDate getEndOfYear() {
	        return LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
	    }

	    @Override
	    public List<DailyReport> getAllByAccountIdOrderByDate(Pageable pageable) {
	        // Fetch all expenses grouped by date
	        List<Object[]> results = expenseRepo.findExpensesOrderByDateWithDetails(getAccountId());

	        // Group expenses by date
	        Map<LocalDate, List<DailyExpenseItem>> groupedByDate = new LinkedHashMap<>();
	        for (Object[] row : results) {
	            DailyExpenseItem item = createExpenseItem(row);
	            LocalDate date = (LocalDate) row[0];

	            groupedByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(item);
	        }

	        // Convert the map to a list of DailyReport objects
	        List<DailyReport> reports = groupedByDate.entrySet().stream()
	            .map(entry -> {
	                LocalDate date = entry.getKey();
	                List<DailyExpenseItem> expenses = entry.getValue();

	                String formattedDate = formatDate(date);
	                double dailyTotal = expenses.stream().mapToDouble(DailyExpenseItem::getAmount).sum();

	                DailyReport report = new DailyReport();
	                report.setFormattedDate(formattedDate);
	                report.setDailyTotal(dailyTotal);
	                report.setExpenses(expenses);

	                return report;
	            })
	            .collect(Collectors.toList());

	     // Implement pagination at the date-group level
	        int start = (int) pageable.getOffset();
	        int end = Math.min(start + pageable.getPageSize(), reports.size());

	        // Check if start is out of bounds
	        if (start > reports.size()) {
	            return Collections.emptyList(); // Return an empty list if the page is out of bounds
	        }

	        return reports.subList(start, end);

	    }

	    
	    private String formatDate(LocalDate date) {
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM");
	    	return date.format(formatter);
	    }
	    
	    private DailyExpenseItem createExpenseItem(Object[] row) {
	        Long id = (Long) row[1];          
	        String description = (String) row[2]; 
	        String category = (String) row[3];   
	        Double amount = (Double) row[4];      

	        return new DailyExpenseItem(id, description, category, amount);
	    }



	
}
