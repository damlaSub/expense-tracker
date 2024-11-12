package com.expensetracker.error;

@SuppressWarnings("serial")
public class AccountNotFoundException
	extends RuntimeException {

    public AccountNotFoundException(String msg) {
	super(msg);
    }

}
