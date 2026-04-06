package com.fintech.finance.dtos;

import lombok.Data;

@Data
public class MonthlyTrendResponseDto {
	
	private String month;
    private String income;
    private String expense;
    
    public MonthlyTrendResponseDto(String month, String income, String expense) {
    	this.month = month;
    	this.income = income;
    	this.expense = expense;
    }
}
