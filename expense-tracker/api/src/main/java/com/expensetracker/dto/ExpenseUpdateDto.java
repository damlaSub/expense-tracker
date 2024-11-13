package com.expensetracker.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ExpenseUpdateDto {
	@Positive
	private Double amount; 
	
    @Size(max = 100)
	private String category;
	
    @Size(max = 100)
	private String description;
	

	public ExpenseUpdateDto() {
		// TODO Auto-generated constructor stub
	    }

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ExpenseUpdateDto [amount=" + amount + ", category=" + category + ", description=" + description + "]";
	}

}
