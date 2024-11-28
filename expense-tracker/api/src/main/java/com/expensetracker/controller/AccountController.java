package com.expensetracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.expensetracker.dto.AccountCreateDto;
import com.expensetracker.dto.AccountSigninDto;
import com.expensetracker.dto.ExpenseLimitUpdateDto;
import com.expensetracker.dto.RefreshTokenRequest;
import com.expensetracker.dto.TokenInfo;
import com.expensetracker.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

	 private AccountService service;

	    public AccountController(AccountService service) {
		this.service = service;
	    }
	    @PostMapping("/sign-up")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void signUp(
		    @Valid @RequestBody AccountCreateDto inputs) {
		service.signUp(inputs);
	    }

	    @PostMapping("/sign-in")
	    public TokenInfo signIn(
		    @Valid @RequestBody AccountSigninDto inputs) {
		return service.signIn(inputs);
	    }

	    @PostMapping("/refresh-token")
	    public TokenInfo refreshtoken(
		    @Valid @RequestBody RefreshTokenRequest request) {
		return service.refreshToken(request);
	    }
	    
	    @PatchMapping("/expense-limit")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void update(@Valid @RequestBody ExpenseLimitUpdateDto inputs) {
		service.updateExpenseLimit( inputs);
	    }
}
