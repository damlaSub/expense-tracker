package com.expensetracker.dto;


public class DailyExpenseItem {
    private Long id;
    private String description;
    private String category;
    private Double amount;

    public DailyExpenseItem(Long id, String description, String category, Double amount) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

	@Override
	public String toString() {
		return "DailyExpenseItem [id=" + id + ", description=" + description + ", category=" + category + ", amount="
				+ amount + "]";
	}
    
}

