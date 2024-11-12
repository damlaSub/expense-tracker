package com.expensetracker.service;


import com.expensetracker.dto.AccountCreateDto;
import com.expensetracker.dto.AccountSigninDto;
import com.expensetracker.dto.RefreshTokenRequest;
import com.expensetracker.dto.TokenInfo;

public interface AccountService {

	public void signUp(AccountCreateDto inputs);

    public Boolean existsByEmail(String email);

    public TokenInfo signIn(AccountSigninDto inputs);

    public TokenInfo refreshToken(
	    RefreshTokenRequest request);

}
