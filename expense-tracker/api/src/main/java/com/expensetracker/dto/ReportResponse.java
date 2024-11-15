package com.expensetracker.dto;

import java.time.LocalDate;
import java.util.Map;

public class ReportResponse {

    private LocalDate startDate;
    private LocalDate endDate;
    private Map<String, Double> categoryTotals; 

    public ReportResponse(LocalDate startDate, LocalDate endDate, Map<String, Double> categoryTotals) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.categoryTotals = categoryTotals;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Map<String, Double> getCategoryTotals() {
        return categoryTotals;
    }

    public void setCategoryTotals(Map<String, Double> categoryTotals) {
        this.categoryTotals = categoryTotals;
    }
}

