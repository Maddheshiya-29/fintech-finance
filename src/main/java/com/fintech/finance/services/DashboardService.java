package com.fintech.finance.services;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fintech.finance.dtos.ApiResponseDto;
import com.fintech.finance.dtos.MonthlyTrendResponseDto;
import com.fintech.finance.dtos.TransactionsDetailDto;
import com.fintech.finance.dtos.TransactionsSummaryDto;
import com.fintech.finance.entities.TransactionsDetail;
import com.fintech.finance.repositories.TransactionsDetailRepository;
import com.fintech.finance.repositories.TransactionsSummaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

	private final UtilService util;
    private final  TransactionsSummaryRepository repoTransSummary;
    private final  TransactionsDetailRepository repoTransDetail;
    
    
	public ResponseEntity<?> overallSummary() {
		BigDecimal totalIncome = Optional.ofNullable(repoTransSummary.findTotalIncome()).orElse(BigDecimal.ZERO);
		BigDecimal totalExpense = Optional.ofNullable(repoTransSummary.findTotalExpense()).orElse(BigDecimal.ZERO);

		BigDecimal netBalance = totalIncome.subtract(totalExpense);
		
		TransactionsSummaryDto dto = TransactionsSummaryDto.builder().totalIncome(totalIncome.toString()).totalExpense(totalExpense.toString()).netBalance(netBalance.toString()).build();
		return ResponseEntity.ok().body(dto);
	}
	
	public ResponseEntity<?> filteredSummary(String year, String quarter) {
		if (!year.matches("\\d{4}-\\d{2}")) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("Invalid year format. Expected: 2026-27").build());
		}
		List<String> validQuarters = List.of("Q1", "Q2", "Q3", "Q4");
		if (!validQuarters.contains(quarter)) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("Invalid quarter. Expected: Q1 type").build());
		}
		BigDecimal totalIncome = Optional.ofNullable(repoTransSummary.findFilteredTotalIncome(year, quarter)).orElse(BigDecimal.ZERO);
		BigDecimal totalExpense = Optional.ofNullable(repoTransSummary.findFilteredTotalExpense(year, quarter)).orElse(BigDecimal.ZERO);

		BigDecimal netBalance = totalIncome.subtract(totalExpense);

		TransactionsSummaryDto dto = TransactionsSummaryDto.builder().totalIncome(totalIncome.toString()).totalExpense(totalExpense.toString()).netBalance(netBalance.toString()).build();
		return ResponseEntity.ok().body(dto);
	}

	public ResponseEntity<?> yearlySummary(String year) {
		if (!year.matches("\\d{4}-\\d{2}")) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("Invalid year format. Expected: 2026-27").build());
		}
		BigDecimal totalIncome = Optional.ofNullable(repoTransSummary.findYearlyTotalIncome(year)).orElse(BigDecimal.ZERO);
		BigDecimal totalExpense = Optional.ofNullable(repoTransSummary.findYearlyTotalExpense(year)).orElse(BigDecimal.ZERO);

		BigDecimal netBalance = totalIncome.subtract(totalExpense);

		TransactionsSummaryDto dto = TransactionsSummaryDto.builder().totalIncome(totalIncome.toString()).totalExpense(totalExpense.toString()).netBalance(netBalance.toString()).build();
		return ResponseEntity.ok().body(dto);
	}

	public ResponseEntity<?> recentTransactions(int limit) {
	    Pageable pageable = PageRequest.of(0, limit);
	    List<TransactionsDetail> details = repoTransDetail.findByDeletedFalseOrderByTransactionDateDesc(pageable).getContent();
	    
	    if(details.isEmpty())
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("No data found").build());

	    List<TransactionsDetailDto> dtos = new ArrayList<TransactionsDetailDto>();
	    
	    for(TransactionsDetail detail : details) {
	    	dtos.add(TransactionsDetailDto.builder().id(detail.getId()).category(detail.getCategory()).amount(detail.getAmount().toString()).transactionDate(util.dateLT.format(detail.getTransactionDate()))
	    												.description(detail.getDescription()).build());
	    }
		return ResponseEntity.ok().body(dtos);
	}
	
	public ResponseEntity<?> getMonthlyTrend(String year) {
        List<Integer> months = repoTransDetail.findDistinctMonthsByYear(year);
        List<MonthlyTrendResponseDto> dtos = new ArrayList<MonthlyTrendResponseDto>();
        
        for(Integer month : months) {
        	BigDecimal totalIncome = new BigDecimal(0.00);
        	BigDecimal totalExpense = new BigDecimal(0.00);

        	List<TransactionsDetail> details = repoTransDetail.findByTransactionMonth(month);
        	for(TransactionsDetail detail : details) {
        		if(detail.getTransactionsSummary().getTransactionType().equals("Income"))
        			totalIncome = totalIncome.add(detail.getAmount());
        		if(detail.getTransactionsSummary().getTransactionType().equals("Expense"))
        			totalExpense = totalExpense.add(detail.getAmount());	
        	}
        	Month monthName = Month.of(month);
        	String name = monthName.name();
        	dtos.add(new MonthlyTrendResponseDto(name, totalIncome.toString(), totalExpense.toString()));
        }
		return ResponseEntity.ok().body(dtos);
    }
}
