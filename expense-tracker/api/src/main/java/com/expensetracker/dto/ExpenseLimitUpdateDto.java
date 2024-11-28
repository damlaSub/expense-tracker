package com.expensetracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ExpenseLimitUpdateDto {

	@NotNull
    @Positive
	private Long id;
	
	@NotNull
    @Positive
	private double expenseLimit;

	public Long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	public double getExpenseLimit() {
		return expenseLimit;
	}

	public void setExpenseLimit(double expenseLimit) {
		this.expenseLimit = expenseLimit;
	}

	@Override
	public String toString() {
		return "ExpenseLimitUpdateDto [id=" + id + ", expenseLimit=" + expenseLimit + "]";
	}
	
	
}
