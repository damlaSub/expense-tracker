package com.expensetracker.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.dto.ExpenseForUpdate;
import com.expensetracker.dto.ExpenseItem;
import com.expensetracker.entities.Expense;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	List<ExpenseItem> findByAccountId(Long currentAccountId);

	ExpenseForUpdate findProjectedById(Long id);
	
}
