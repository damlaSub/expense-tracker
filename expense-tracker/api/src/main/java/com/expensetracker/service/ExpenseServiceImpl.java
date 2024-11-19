package com.expensetracker.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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

@Service
@Transactional(readOnly = true)
public class ExpenseServiceImpl implements ExpenseService{

	private final ExpenseRepository expenseRepo;
	private final AccountRepository accountRepo;
	
	public ExpenseServiceImpl(ExpenseRepository expenseRepo, AccountRepository accountRepo) {
		this.expenseRepo = expenseRepo;
		this.accountRepo = accountRepo;
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
	}

	@Override
	public List<ExpenseItem> getAllByAccount() {
		return expenseRepo.findByAccountId(getAccountId());
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
		
	}
	
	@Override
	public DailyReport getMostRecentReport() {
	   
	    List<Object[]> results = expenseRepo.findMostRecentExpenses(getAccountId());
	    
	    if (results.isEmpty()) {
	        return null; // TODO
	    }
	    
	    List<DailyExpenseItem> expenses = results.stream()
	            .map(row -> new DailyExpenseItem(
	                (Long) row[1],        
	                (String) row[2],      
	                (String) row[3],      
	                (Double) row[4]       
	            ))
	            .toList();
	    
	    LocalDate date = (LocalDate) results.get(0)[0];
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM");
	    String formattedDate = date.format(formatter);
	    
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

	
}
