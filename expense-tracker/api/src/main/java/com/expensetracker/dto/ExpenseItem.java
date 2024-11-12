package com.expensetracker.dto;

import java.time.LocalDate;

import com.expensetracker.entities.ExpenseCategory;

public interface ExpenseItem {

	Long getId();
	
    String getAmount();

    String getDescription();

    LocalDate getDate();

    String getCategory();

}
