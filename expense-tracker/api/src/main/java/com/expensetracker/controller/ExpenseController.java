package com.expensetracker.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.dto.DailyReport;
import com.expensetracker.dto.ExpenseCreateDto;
import com.expensetracker.dto.ExpenseForUpdate;
import com.expensetracker.dto.ExpenseItem;
import com.expensetracker.dto.ExpenseUpdateDto;
import com.expensetracker.dto.ReportResponse;
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
	public List<ExpenseItem> getAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
		    @RequestParam(value = "size", defaultValue = "10") int size){
		Pageable pageable = PageRequest.of(page, size);
		return service.getAllByAccount(pageable);
	}
	
	@GetMapping("/by-date")
    public List<DailyReport> getExpensesByDate() {
        return service.getAllByAccountIdOrderByDate();
    }
	
	@PostMapping("/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void add(@Valid @RequestBody ExpenseCreateDto inputs) {
		service.add(inputs);
	}
	
	@DeleteMapping("/{id}/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
	@GetMapping("/{id}/for-update")
	public ExpenseForUpdate getForUpdate(@PathVariable Long id) {
		return service.getForUpdate(id);
	}
	
	@PatchMapping("/{id}/update")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long id, @Valid @RequestBody ExpenseUpdateDto inputs) {
		service.update(id, inputs);
	}
	
	@GetMapping("/reports-day")
    public DailyReport getMostRecentReport() {
        return service.getMostRecentReport();
    }
	
	@GetMapping("/reports-week")
    public ReportResponse getThisWeekReport() {
        return service.getThisWeekReport();
    }
	
	@GetMapping("/reports-month")
    public ReportResponse getThisMonthReport() {
        return service.getThisMonthReport();
    }
	
	@GetMapping("/reports-year")
    public ReportResponse getThisYearReport() {
        return service.getThisYearReport();
    }
}
