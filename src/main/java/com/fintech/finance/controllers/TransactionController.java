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
import com.fintech.finance.dtos.TransactionsDetailDto;
import com.fintech.finance.dtos.TransactionsSummaryDto;
import com.fintech.finance.services.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionService servTransaction;
	
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
	@GetMapping("/user-records")
    public ResponseEntity<?> userRecords(@RequestParam String year, @RequestParam String quarter, @RequestParam String transactionType) {
    	try {
    		return servTransaction.userRecords(year, quarter, transactionType);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
	
	@PreAuthorize("hasAnyRole('AyDMIN','ANALYST')")
	@GetMapping("/all-transaction-summary")
    public ResponseEntity<?> allTransactionSummary(@RequestParam String year, @RequestParam String quarter, @RequestParam String transactionType, @RequestParam int page, int size) {
    	try {
    		return servTransaction.allTransactionSummary(year, quarter, transactionType, page, size);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
	
	//In this id will be the id got in /transaction/all-transaction-summary
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
	@GetMapping("/transaction-details/{id}")
    public ResponseEntity<?> transactionDetails(@PathVariable Long id, @RequestParam String year, @RequestParam String quarter, @RequestParam String transactionType) {
    	try {
    		return servTransaction.transactionDetails(id, year, quarter, transactionType);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
	
	//In this id will be the id got in /transaction/transaction-summary
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PatchMapping("/update-transaction-summary/{id}")
    public ResponseEntity<?> updateTransactionSummary(@PathVariable Long id, @RequestBody TransactionsSummaryDto dto) {
    	try {
    		return servTransaction.updateTransactionSummary(id, dto);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
	
	
	//In this id will be the id got in /transaction//transaction-details/{id}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PatchMapping("/update-transaction-detail/{id}")
    public ResponseEntity<?> updateTransactionDetail(@PathVariable Long id, @RequestBody TransactionsDetailDto dto) {
    	try {
    		return servTransaction.updateTransactionDetail(id, dto);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
	 }
		
	//In this id will be the id got in /transaction/transaction-summary
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PatchMapping("/delete-transaction-summary/{id}")
    public ResponseEntity<?> deleteTransactionSummary(@PathVariable Long id) {
    	try {
    		return servTransaction.deleteTransactionSummary(id);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
		
		
	//In this id will be the id got in /transaction//transaction-details/{id}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PatchMapping("/delete-transaction-detail/{id}")
    public ResponseEntity<?> deleteTransactionDetail(@PathVariable Long id) {
    	try {
    		return servTransaction.deleteTransactionDetail(id);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("There is something wrong !!").build());
    	}
    }
}
