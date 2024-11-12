package com.expensetracker.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ExpenseCreateDto {
	@NotNull
	@Positive
	private Double amount; 
	
	@NotBlank
    @Size(max = 100)
	private String category;
	
	private String description;
	

	public ExpenseCreateDto() {
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
		return "ExpenseCreateDto [amount=" + amount + ", category=" + category + ", description=" + description + "]";
	}

	
}
