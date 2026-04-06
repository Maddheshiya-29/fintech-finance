package com.fintech.finance.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fintech.finance.dtos.ApiResponseDto;
import com.fintech.finance.dtos.TransactionsDetailDto;
import com.fintech.finance.dtos.TransactionsSummaryDto;
import com.fintech.finance.entities.TransactionsDetail;
import com.fintech.finance.entities.TransactionsSummary;
import com.fintech.finance.entities.User;
import com.fintech.finance.repositories.TransactionsDetailRepository;
import com.fintech.finance.repositories.TransactionsSummaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final UtilService util;
    private final  TransactionsSummaryRepository repoTransSummary;
    private final  TransactionsDetailRepository repoTransDetail;
    
    
	public ResponseEntity<?> userRecords(String year, String quarter, String transactionType) {
		User usr = util.getLoggedInUser();
		if(usr==null)
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("No such user found").build());

		String paramCheckResponse = checkParam(year, quarter, transactionType);
		if(!paramCheckResponse.equals("ALL OK"))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message(paramCheckResponse).build());

		TransactionsSummary data = repoTransSummary.findByDeletedAndFinancialYearAndQuarterAndTransactionTypeAndUser(false, year, quarter, transactionType, usr);
		List<TransactionsDetail> details = repoTransDetail.findAllByDeletedAndTransactionsSummary(false, data);
		
		TransactionsSummaryDto dto = TransactionsSummaryDto.builder().id(data.getId()).amount(data.getAmount().toString()).financialYear(data.getFinancialYear()).name(data.getName())
																	.quarter(data.getQuarter()).transactionType(data.getTransactionType()).build();
		
		List<TransactionsDetailDto> detailDtos = new ArrayList<TransactionsDetailDto>();
		
		for(TransactionsDetail detail : details) {
			detailDtos.add(TransactionsDetailDto.builder().id(detail.getId()).category(detail.getCategory()).amount(detail.getAmount().toString())
															.description(detail.getDescription()).transactionDate(util.dateLT.format(detail.getTransactionDate())).build());
		}
		dto.setTransactionDetailDtos(detailDtos);
		
		return ResponseEntity.ok().body(dto);
	}
	
	public ResponseEntity<?> transactionDetails(Long id, String year, String quarter, String transactionType) {
		String paramCheckResponse = checkParam(year, quarter, transactionType);
		if(!paramCheckResponse.equals("ALL OK"))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message(paramCheckResponse).build());

		TransactionsSummary data = repoTransSummary.findByDeletedAndId(false, id);
		List<TransactionsDetail> details = repoTransDetail.findAllByDeletedAndTransactionsSummary(false, data);
		
		TransactionsSummaryDto dto = TransactionsSummaryDto.builder().id(data.getId()).amount(data.getAmount().toString()).financialYear(data.getFinancialYear())
																		.name(data.getName()).quarter(data.getQuarter()).transactionType(data.getTransactionType()).build();
		
		List<TransactionsDetailDto> detailDtos = new ArrayList<TransactionsDetailDto>();
		
		for(TransactionsDetail detail : details) {
			detailDtos.add(TransactionsDetailDto.builder().id(detail.getId()).category(detail.getCategory()).amount(detail.getAmount().toString())
															.description(detail.getDescription()).transactionDate(util.dateLT.format(detail.getTransactionDate())).build());
		}
		dto.setTransactionDetailDtos(detailDtos);
		
		return ResponseEntity.ok().body(dto);
	}

	public ResponseEntity<?> allTransactionSummary(String year, String quarter, String transactionType, int page, int size) {
		String paramCheckResponse = checkParam(year, quarter, transactionType);
		if(!paramCheckResponse.equals("ALL OK"))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message(paramCheckResponse).build());
		
		Pageable pageable = PageRequest.of(page, size);

	    Page<TransactionsSummary> transactionsPage = repoTransSummary.findAllByDeletedAndFinancialYearAndQuarterAndTransactionTypeOrderByName(false, year, quarter, transactionType, pageable);

	    List<TransactionsSummaryDto> dtos = transactionsPage.getContent().stream()
												            .map(transaction -> TransactionsSummaryDto.builder()
												                    .id(transaction.getId())
												                    .amount(transaction.getAmount().toString())
												                    .financialYear(transaction.getFinancialYear())
												                    .name(transaction.getName())
												                    .quarter(transaction.getQuarter())
												                    .transactionType(transaction.getTransactionType())
												                    .build())
												            .toList();
		return ResponseEntity.ok().body(dtos);
	}
	
	private String checkParam(String year, String quarter, String transactionType) {
		if (!year.matches("\\d{4}-\\d{2}")) {
	        return "Invalid year format. Expected: 2026-27";
		}
		List<String> validQuarters = List.of("Q1", "Q2", "Q3", "Q4");
		if (!validQuarters.contains(quarter)) {
	        return "Invalid quarter. Expected: Q1 type";
		}
		List<String> validTypes = List.of("Income", "Expense");
		if (!validTypes.contains(transactionType)) {
	        return "Invalid transaction type. Expected: Income or Expense.";
		}
		return "ALL OK";
	}

	public ResponseEntity<?> updateTransactionSummary(Long id, TransactionsSummaryDto dto) {
		TransactionsSummary summary = repoTransSummary.findByDeletedAndId(false, id);
		
		if(summary==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("No such data found.").build());
		}

		if(!util.isStringNullOrBlank(dto.getFinancialYear()))
			summary.setFinancialYear(dto.getFinancialYear());
		if(!util.isStringNullOrBlank(dto.getQuarter()))
			summary.setQuarter(dto.getQuarter());
		if(!util.isStringNullOrBlank(dto.getTransactionType()))
			summary.setTransactionType(dto.getTransactionType());
		
		repoTransSummary.save(summary);
		return ResponseEntity.ok().body(ApiResponseDto.builder().status(true).message("Updated successfully.").build());
	}

	public ResponseEntity<?> updateTransactionDetail(Long id, TransactionsDetailDto dto) {
		TransactionsDetail detail = repoTransDetail.findByDeletedAndId(false, id);
		if(detail==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("No such data found.").build());
		}
		
		if(!util.isStringNullOrBlank(dto.getCategory()))
			detail.setCategory(dto.getCategory().trim());
		if(!util.isStringNullOrBlank(dto.getDescription()))
			detail.setDescription(dto.getDescription());
		if(!util.isStringNullOrBlank(dto.getTransactionDate()))
			detail.setTransactionDate(LocalDate.parse(dto.getTransactionDate()));
		if(!util.isStringNullOrBlank(dto.getAmount())) {
			detail.setAmount(new BigDecimal(dto.getAmount()));
		}
		repoTransDetail.save(detail);
		
		if(!util.isStringNullOrBlank(dto.getAmount())) {
			TransactionsSummary summary = detail.getTransactionsSummary(); 
			List<TransactionsDetail> details = repoTransDetail.findAllByDeletedAndTransactionsSummary(false, summary);
			BigDecimal sum = BigDecimal.ZERO;
			for(TransactionsDetail transac : details) {
				sum= sum.add(transac.getAmount());
			}
			summary.setAmount(sum);
			repoTransSummary.save(summary);
		}
		return ResponseEntity.ok().body(ApiResponseDto.builder().status(true).message("Updated successfully.").build());
	}
	
	public ResponseEntity<?> deleteTransactionDetail(Long id) {
		TransactionsDetail detail = repoTransDetail.findByDeletedAndId(false, id);
		if(detail==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("No such data found.").build());
		}
		detail.setDeleted(true);
		repoTransDetail.save(detail);
		
		TransactionsSummary summary = detail.getTransactionsSummary(); 
		List<TransactionsDetail> details = repoTransDetail.findAllByDeletedAndTransactionsSummary(false, summary);
		BigDecimal sum = BigDecimal.ZERO;
		for(TransactionsDetail transac : details) {
			sum= sum.add(transac.getAmount());
		}
		summary.setAmount(sum);
		repoTransSummary.save(summary);
		
		return ResponseEntity.ok().body(ApiResponseDto.builder().status(true).message("Deleted successfully.").build());
	}
	
	public ResponseEntity<?> deleteTransactionSummary(Long id) {
		TransactionsSummary summary = repoTransSummary.findByDeletedAndId(false, id);
		
		if(summary==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("No such data found.").build());
		}
		summary.setDeleted(true);
		repoTransSummary.save(summary);
		
		List<TransactionsDetail> details = repoTransDetail.findAllByDeletedAndTransactionsSummary(false, summary);
		
		for(TransactionsDetail detail : details) {
			detail.setDeleted(true);
			repoTransDetail.save(detail);
		}
		return ResponseEntity.ok().body(ApiResponseDto.builder().status(true).message("Deleted successfully.").build());
	}
}
