package com.expensetracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.expensetracker.dto.ExpenseCreateDto;
import com.expensetracker.dto.ExpenseForUpdate;
import com.expensetracker.dto.ExpenseItem;
import com.expensetracker.dto.ExpenseUpdateDto;
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
	
	
}
