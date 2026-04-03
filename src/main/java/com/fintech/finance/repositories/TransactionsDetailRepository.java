package com.fintech.finance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintech.finance.entities.TransactionsDetail;

public interface TransactionsDetailRepository extends JpaRepository<TransactionsDetail, Long>{

}
