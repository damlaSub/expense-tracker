package com.expensetracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.dto.ExpenseCreateDto;
import com.expensetracker.dto.ExpenseItem;
import com.expensetracker.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
	
	private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
	this.service = service;
    }

	@GetMapping 
	public List<ExpenseItem> getAll(){
		return service.getAllByAccount();
	}
	
	@PostMapping("/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void add(@Valid @RequestBody ExpenseCreateDto inputs) {
		service.add(inputs);
	}
	
	
}
