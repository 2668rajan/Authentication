package com.sportseventmanagement.request;

public class LoginRequest {
	
	private String Username;
	private String password;
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginRequest(String username, String password) {
		super();
		Username = username;
		this.password = password;
	}
	public LoginRequest() {
		super();
	}
	@Override
	public String toString() {
		return "LoginRequest [Username=" + Username + ", password=" + password + "]";
	}
	

}
