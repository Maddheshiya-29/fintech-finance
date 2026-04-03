package com.fintech.finance.dtos;

import lombok.Data;

@Data
public class TransactionsSummaryDto {

	private Long id;
	private String quarter;
	private String financialYear;
	private String transactionType;
	private String amount;
	private String netBalance;
	private String name;
	private Long userId;
}
