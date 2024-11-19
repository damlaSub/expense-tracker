package com.expensetracker.dto;


import java.util.List;

public class DailyReport {
	
	 private String formattedDate;;
	
	private List<DailyExpenseItem> expenses;
	
	private Double dailyTotal;

	public String getFormattedDate() {
		return formattedDate;
	}

	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}

	public List<DailyExpenseItem> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<DailyExpenseItem> expenses) {
		this.expenses = expenses;
	}

	public Double getDailyTotal() {
		return dailyTotal;
	}

	public void setDailyTotal(Double dailyTotal) {
		this.dailyTotal = dailyTotal;
	}

	@Override
	public String toString() {
		return "DailyReport [formattedDate=" + formattedDate + ", expenses=" + expenses + ", dailyTotal=" + dailyTotal
				+ "]";
	}

	

	
}
