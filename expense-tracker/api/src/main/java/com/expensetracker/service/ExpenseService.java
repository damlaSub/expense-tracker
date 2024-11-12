package com.expensetracker.service;

import java.util.List;

import com.expensetracker.dto.ExpenseCreateDto;
import com.expensetracker.dto.ExpenseItem;


public interface ExpenseService {

	List<ExpenseItem> getAllByAccount();

	void add(ExpenseCreateDto inputs);
	
	void delete(Long id);
}
