package com.fintech.finance.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.finance.dtos.ApiResponseDto;
import com.fintech.finance.services.DashboardService;
import com.fintech.finance.services.UtilService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

	private final UtilService util;
	private final DashboardService servDashboard;
	
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
	@GetMapping("/overall-summary")
    public ResponseEntity<?> overallSummary() {
    	try {
    		return servDashboard.overallSummary();
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
	
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
	@GetMapping("/filtered-summary")
    public ResponseEntity<?> filteredSummary(@RequestParam String year, @RequestParam String quarter) {
    	try {
    		if(util.isStringNullOrBlank(quarter) || util.isStringNullOrBlank(year))
    	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("PLease fill all the mandatory fields").build());

    		return servDashboard.filteredSummary(year, quarter);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }

	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
	@GetMapping("/yearly-summary")
    public ResponseEntity<?> yearlySummary(@RequestParam String year) {
    	try {
    		if(util.isStringNullOrBlank(year))
    	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("PLease fill all the mandatory fields").build());

    		return servDashboard.yearlySummary(year);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
	
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
	@GetMapping("/recentTransactions")
    public ResponseEntity<?> recentTransactions(@RequestParam int limit) {
    	try {
    		return servDashboard.recentTransactions(limit);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
	
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/monthly-trend")
    public ResponseEntity<?> getMonthlyTrend(@RequestParam String year) {
		 try {
		 if(util.isStringNullOrBlank(year))
 	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("PLease fill all the mandatory fields").build());

	        return servDashboard.getMonthlyTrend(year);
		 }
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
	 }
}
