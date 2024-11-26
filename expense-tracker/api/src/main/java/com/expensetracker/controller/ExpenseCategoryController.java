package com.expensetracker.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.entities.enums.ExpenseCategory;

@RestController
@RequestMapping("/expense-categories")
public class ExpenseCategoryController {

	@GetMapping
	public List<String> getAllExpenseCategories() {
	    return Arrays.stream(ExpenseCategory.values())
	                 .map(ExpenseCategory::getName)
	                 .collect(Collectors.toList());
	}
}
