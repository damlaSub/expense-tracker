package com.expensetracker.service;

import java.util.List;

import com.expensetracker.dto.DailyReport;
import com.expensetracker.dto.ExpenseCreateDto;
import com.expensetracker.dto.ExpenseForUpdate;
import com.expensetracker.dto.ExpenseItem;
import com.expensetracker.dto.ExpenseUpdateDto;
import com.expensetracker.dto.ReportResponse;



public interface ExpenseService {

	List<ExpenseItem> getAllByAccount();

	void add(ExpenseCreateDto inputs);
	
	void delete(Long id);
	
	void update(Long id, ExpenseUpdateDto inputs);

	ExpenseForUpdate getForUpdate(Long id);
	
	ReportResponse getThisWeekReport();
	
	ReportResponse getThisMonthReport();
	
	ReportResponse getThisYearReport();
	
	DailyReport getMostRecentReport();

	List<DailyReport> getAllByAccountIdOrderByDate();
}
