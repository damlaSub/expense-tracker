package com.expensetracker.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.dto.ExpenseItem;
import com.expensetracker.service.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
	
	private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
	this.service = service;
    }

	@GetMapping 
	public Collection<ExpenseItem> getAll(){
		return service.getAllByAccount();
	}
}
