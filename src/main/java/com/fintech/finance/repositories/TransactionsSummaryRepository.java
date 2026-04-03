package com.fintech.finance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintech.finance.entities.TransactionsSummary;

public interface TransactionsSummaryRepository extends JpaRepository<TransactionsSummary, Long>{

}
