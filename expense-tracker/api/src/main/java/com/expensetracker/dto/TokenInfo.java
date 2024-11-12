package com.expensetracker.dto;

public class TokenInfo {

	private String token;

    private String firstName;

    private String refreshToken;

    public TokenInfo() {
	// TODO Auto-generated constructor stub
	super();
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "TokenInfo [token=" + token + ", firstName=" + firstName + ", refreshToken=" + refreshToken + "]";
	}
    
    
}
