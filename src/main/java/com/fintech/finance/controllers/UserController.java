package com.fintech.finance.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.finance.dtos.ApiResponseDto;
import com.fintech.finance.dtos.LoginDto;
import com.fintech.finance.dtos.LoginResponseDto;
import com.fintech.finance.dtos.UserDto;
import com.fintech.finance.services.UserService;
import com.fintech.finance.services.UtilService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UtilService util;
	private final UserService userService;
	
	@PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto dto) {
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/username-exists")
    public ResponseEntity<ApiResponseDto> usernameExists(@RequestParam String username) {
    	try {
    		if(util.isStringNullOrBlank(username)) {
        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("Please fill all mandatory fields !!").build());
    		}
    		return userService.usernameExists(username);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto dto) {
    	try {
    		return userService.register(dto);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/roles")
    public ResponseEntity<?> roles() {
    	try {
    		return userService.roles();
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
}
