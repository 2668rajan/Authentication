package com.sportseventmanagement.response;

public class JWTResponse {
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JWTResponse(String token) {
		super();
		this.token = token;
	}

	public JWTResponse() {
		super();
	}

	@Override
	public String toString() {
		return "JWTResponse [token=" + token + "]";
	}
	

}
