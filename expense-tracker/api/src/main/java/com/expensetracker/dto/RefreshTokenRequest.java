package com.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {

	@NotBlank
    private String refreshToken;

    public String getRefreshToken() {
	return refreshToken;
    }

    @SuppressWarnings("unused")
	private void setRefreshToken(String refreshToken) {
    	// Not updatable
	this.refreshToken = refreshToken;
    }
}
