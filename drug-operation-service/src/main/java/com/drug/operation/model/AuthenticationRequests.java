package com.drug.operation.model;

public class AuthenticationRequests {

	private String username;
	private String password;
	
	public AuthenticationRequests() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuthenticationRequests(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
