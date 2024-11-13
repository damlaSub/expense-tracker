package com.expensetracker.service;

import java.util.List;

import com.expensetracker.dto.ExpenseCreateDto;
import com.expensetracker.dto.ExpenseForUpdate;
import com.expensetracker.dto.ExpenseItem;
import com.expensetracker.dto.ExpenseUpdateDto;

import jakarta.validation.Valid;


public interface ExpenseService {

	List<ExpenseItem> getAllByAccount();

	void add(ExpenseCreateDto inputs);
	
	void delete(Long id);
	
	void update(Long id, ExpenseUpdateDto inputs);

	ExpenseForUpdate getForUpdate(Long id);
}
