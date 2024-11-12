package com.expensetracker.service;

import java.util.Set;

import com.expensetracker.dto.ExpenseItem;

public interface ExpenseService {


	Set<ExpenseItem> getAllByAccount();
}
