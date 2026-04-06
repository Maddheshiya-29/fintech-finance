package com.fintech.finance.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionsSummaryDto {

	private Long id;
	private String quarter;
	private String financialYear;
	private String transactionType;
	private String amount;
	private String netBalance;
	private String name;
	private Long userId;
	
	private String totalExpense;
	private String totalIncome;
	private List<TransactionsDetailDto> transactionDetailDtos;
}
