package com.fintech.finance.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TransactionsDetailDto {

	private Long id;
    private LocalDate transactionDate;
    private String description;
    private String amount;
    private String category;
    private String name;
    private Long transactionsSummaryId;
}
