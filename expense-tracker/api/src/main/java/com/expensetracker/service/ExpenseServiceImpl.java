package com.expensetracker.service;

import java.util.Set;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.expensetracker.dto.ExpenseItem;
import com.expensetracker.entities.Expense;
import com.expensetracker.repository.ExpenseRepository;

@Service
@Transactional(readOnly = true)
public class ExpenseServiceImpl implements ExpenseService{

	private final ExpenseRepository expenseRepo;
	
	public ExpenseServiceImpl(ExpenseRepository expenseRepo) {
		this.expenseRepo = expenseRepo;
	}

	@Override
	public Set<ExpenseItem> getAllByAccount() {
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
	
}
