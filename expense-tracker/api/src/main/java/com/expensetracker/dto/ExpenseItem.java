package com.expensetracker.dto;

import java.time.LocalDate;

public interface ExpenseItem {

	Long getId();
	
	Double getAmount();

    String getDescription();

    LocalDate getDate();

    String getCategory();

}
