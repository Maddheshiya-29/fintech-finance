package com.fintech.finance.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionsDetailDto {

	private Long id;
    private String transactionDate;
    private String description;
    private String amount;
    private String category;
    private Long transactionsSummaryId;
}
