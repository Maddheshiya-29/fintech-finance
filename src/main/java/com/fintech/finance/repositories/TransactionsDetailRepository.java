package com.fintech.finance.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fintech.finance.entities.TransactionsDetail;
import com.fintech.finance.entities.TransactionsSummary;

public interface TransactionsDetailRepository extends JpaRepository<TransactionsDetail, Long>{

	public Page<TransactionsDetail> findByDeletedFalseOrderByTransactionDateDesc(Pageable pageable);

	public List<TransactionsDetail> findAllByDeletedAndTransactionsSummary(boolean deleted, TransactionsSummary data);

	public TransactionsDetail findByDeletedAndId(boolean deleted, Long id);
	
	@Query(value="SELECT DISTINCT MONTH(e.transaction_date) FROM transactions_detail e WHERE YEAR(e.transaction_date) =:year ORDER BY MONTH(e.transaction_date)", nativeQuery=true)
	public List<Integer> findDistinctMonthsByYear(@Param("year") String year);
	
	@Query(value="SELECT e.* FROM transactions_detail e WHERE MONTH(e.transaction_date) =:month", nativeQuery=true)
	public List<TransactionsDetail> findByTransactionMonth(@Param("month") int month);

}
