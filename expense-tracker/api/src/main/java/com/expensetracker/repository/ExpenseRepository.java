package com.expensetracker.repository;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensetracker.dto.ExpenseItem;
import com.expensetracker.entities.Expense;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	Set<ExpenseItem> findByAccountId(Long currentAccountId);

	
}
