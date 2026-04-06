package com.fintech.finance.dtos;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDto {

	private Long id;
	
	@NotBlank (message = "Name is required")
	private String name;
	
	@NotEmpty
	@Pattern(regexp="[1-9]{1}[0-9]{9}", message="Mobile Number must be in 10 digit number only !!")
	private String mobile;
	
	@NotBlank (message = "Email is required")
    @Email(message = "Invalid email format")
	private String email;
	
	@NotBlank (message = "Username is required")
	private String username;
	
	@NotBlank (message = "Password is required")
	private String password;
	
	@NotBlank (message = "Confirm Password is required")
	private String confirmPassword;
	
	@NotBlank (message = "Role is required")
	private String role;
	
	private Boolean active;
	private String oldPassword;
	private List<String> roles;
}
