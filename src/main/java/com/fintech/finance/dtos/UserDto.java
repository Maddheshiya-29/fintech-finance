package com.fintech.finance.dtos;

import lombok.Data;

@Data
public class UserDto {

	private Long id;
	private String name;
	private String mobile;
	private String email;
	private String username;
	private String password;
	private String role;
	private boolean active;
}
