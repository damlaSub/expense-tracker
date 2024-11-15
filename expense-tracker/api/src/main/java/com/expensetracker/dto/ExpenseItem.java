package com.expensetracker.dto;

import java.time.LocalDate;

import com.expensetracker.entities.enums.ExpenseCategory;

public interface ExpenseItem {

	Long getId();
	
	Double getAmount();

    String getDescription();

    LocalDate getDate();

    String getCategory();

}
