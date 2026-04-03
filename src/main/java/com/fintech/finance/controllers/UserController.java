package com.fintech.finance.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.finance.dtos.LoginResponseDto;
import com.fintech.finance.dtos.UserDto;
import com.fintech.finance.services.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	 @PostMapping("/login")
	    public ResponseEntity<LoginResponseDto> login(@RequestBody UserDto dto) {
	    	try {
	    		return userService.authenticate(dto);
	    	}
	    	catch (BadCredentialsException e) {
	    		return ResponseEntity.ofNullable(new LoginResponseDto("Invalid Credential !!"));
	    	}
	    	catch (Exception e) {
	    		System.out.println(e.getMessage());
	    		return ResponseEntity.ofNullable(new LoginResponseDto("There is something wrong !!"));
	    	}
	    }
}
