package com.expensetracker.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.expensetracker.dto.AccountCreateDto;
import com.expensetracker.dto.AccountSigninDto;
import com.expensetracker.dto.RefreshTokenRequest;
import com.expensetracker.dto.TokenInfo;
import com.expensetracker.entities.Account;
import com.expensetracker.error.AccountNotFoundException;
import com.expensetracker.repository.AccountRepository;
import com.expensetracker.util.AuthHelper;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
	
	private final AuthHelper authHelper;

    private final AccountRepository accountRepository;

	public AccountServiceImpl(AuthHelper authHelper,
		    AccountRepository accountRepository) {
		this.authHelper = authHelper;
		this.accountRepository = accountRepository;

	    }
	
	 @Override
	    @Transactional
	    public void signUp(AccountCreateDto inputs) {
		Account account = new Account();
		account.setFirstName(inputs.getFirstName());
		account.setLastName(inputs.getLastName());
		account.setEmail(inputs.getEmail());
		String hashPassword = authHelper
			.encode(inputs.getPassword());
		account.setPassword(hashPassword);
		accountRepository.save(account);
	    }
	 
	 @Override
	    @Transactional
	    public TokenInfo signIn(AccountSigninDto inputs) {
		String identifier = inputs.getEmail();
		String candidate = inputs.getPassword();
		Optional<Account> account = accountRepository
			.findByEmailIgnoreCase(identifier);
		if (account.isPresent()) {
		    boolean match = authHelper.matches(candidate,
			    account.get().getPassword());
		    if (match) {
			return createTokenFromAccount(
				account.get());
		    } else {
			throw new BadCredentialsException(
				"Invalid email or password");
		    }
		} else {
		    throw new BadCredentialsException(
			    "Invalid email or password");
		}
	    }

	    @Override
	    public TokenInfo refreshToken(
		    RefreshTokenRequest request) {
	    	String idAsString = authHelper.getIdFromToken(request.getRefreshToken());
	        Long id = Long.parseLong(idAsString);
	        Account account = accountRepository.findById(id)
	                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
	        return createTokenFromAccount(account);
	    }

	    TokenInfo createTokenFromAccount(Account account) {
		try {
		    String id = String.valueOf(account.getId());
		    String token = authHelper.createJWT(id);
		    TokenInfo tokenInfo = new TokenInfo();
		    tokenInfo.setToken(token);
		    tokenInfo.setFirstName(account.getFirstName());
		    String refreshToken;
		    refreshToken = authHelper.createRefreshJWT(id);
		    tokenInfo.setRefreshToken(refreshToken);
		    return tokenInfo;
		} catch (Exception e) {
		    throw new RuntimeException(
			    "An error occurred during token creation",
			    e);
		}
	    }

	    @Override
	    public Boolean existsByEmail(String email) {
		return this.accountRepository
			.existsByEmailIgnoreCase(email.toString());
	    }

	    
	}