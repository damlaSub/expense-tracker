package com.expensetracker.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(AccountNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleAccountNotFoundException(
	    AccountNotFoundException ex) {
	ErrorMessage errorMessage = new ErrorMessage(
		HttpStatus.UNAUTHORIZED.value(),
		"Unauthoried", ex.getMessage());
	return new ResponseEntity<>(errorMessage,
		HttpStatus.UNAUTHORIZED);
    }
    
    
}
