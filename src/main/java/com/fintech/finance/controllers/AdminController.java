package com.fintech.finance.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.finance.dtos.ApiResponseDto;
import com.fintech.finance.dtos.UserDto;
import com.fintech.finance.services.AdminService;
import com.fintech.finance.services.UtilService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final UtilService util;
	private final AdminService servAdmin;
	
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
	@GetMapping("/users")
    public ResponseEntity<?> users(@RequestParam boolean active, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    	try {
    		return servAdmin.users(active, page, size);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
    	try {
    		if(dto==null) {
        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("Request body cannot be empty").build());
    		}
    		return servAdmin.updateUser(id, dto);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
	
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
	@GetMapping("/user-by-username")
    public ResponseEntity<?> userByUsername(@RequestParam String username) {
    	try {
    		if(util.isStringNullOrBlank(username)) {
        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("Please fill all mandatory fields !!").build());
    		}
    		return servAdmin.userByUsername(username);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }	
}
