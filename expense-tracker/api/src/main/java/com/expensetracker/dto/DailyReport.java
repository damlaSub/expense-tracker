package com.expensetracker.dto;

import java.time.LocalDate;
import java.util.List;

public class DailyReport {
	
	private LocalDate date;
	
	private List<ExpenseItem> expenses;
	
	private Double dailyTotal;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<ExpenseItem> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<ExpenseItem> expenses) {
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
		return "DailyReport [date=" + date + ", expenses=" + expenses + ", dailyTotal=" + dailyTotal + "]";
	}
	
}
