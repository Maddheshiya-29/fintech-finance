package com.fintech.finance.dtos;

import lombok.Data;

@Data
public class LoginResponseDto {

	private String userId;
	private String token;
	private String name;
	private String role;
	private String type;
	
	private boolean response=false;
	private String message;
	
	private String email;
	private String mobile;
	
	public LoginResponseDto() {}
	
	public LoginResponseDto(String message) {
		this.message = message;
	}
	
	public LoginResponseDto(boolean response, String message, String userId, String name, String token, String role) {
		this.name = name;
		this.role = role;
		this.token = token;
		this.userId = userId;
		this.message = message;
		this.response = response;
	}
}
