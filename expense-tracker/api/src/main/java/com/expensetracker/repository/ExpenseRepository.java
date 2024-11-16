package com.expensetracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.expensetracker.dto.ExpenseForUpdate;
import com.expensetracker.dto.ExpenseItem;
import com.expensetracker.entities.Expense;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	List<ExpenseItem> findByAccountId(Long currentAccountId);

	ExpenseForUpdate findProjectedById(Long id);
	
	@Query("SELECT e.id AS id, e.amount AS amount, e.description AS description, e.date AS date, e.category AS category " +
            "FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate AND e.account.id = :accountId")
	List<ExpenseItem> findExpenseItemsByDateRange(LocalDate startDate, LocalDate endDate, Long accountId);
}
