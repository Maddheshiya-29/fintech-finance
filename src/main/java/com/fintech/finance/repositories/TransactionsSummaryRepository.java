package com.fintech.finance.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fintech.finance.entities.TransactionsSummary;
import com.fintech.finance.entities.User;

public interface TransactionsSummaryRepository extends JpaRepository<TransactionsSummary, Long>{

	@Query(value="SELECT SUM(amount) AS total_amount FROM orders WHERE transaction_type = 'Income'", nativeQuery=true)
	public BigDecimal findTotalIncome();
	
	@Query(value="SELECT SUM(amount) AS total_amount FROM orders WHERE transaction_type = 'Expense'", nativeQuery=true)
	public BigDecimal findTotalExpense();
	
	@Query(value="SELECT SUM(amount) AS total_amount FROM orders WHERE transaction_type = 'Income' and quarter=:quarter and financial_yaer=:year", nativeQuery=true)
	public BigDecimal findFilteredTotalIncome(@Param("year") String year, @Param("quarter") String quarter);
	
	@Query(value="SELECT SUM(amount) AS total_amount FROM orders WHERE transaction_type = 'Expense' and quarter=:quarter and financial_yaer=:year", nativeQuery=true)
	public BigDecimal findFilteredTotalExpense(@Param("year") String year, @Param("quarter") String quarter);

	@Query(value="SELECT SUM(amount) AS total_amount FROM orders WHERE transaction_type = 'Income' and financial_yaer=:year", nativeQuery=true)
	public BigDecimal findYearlyTotalIncome(@Param("year") String year);
	
	@Query(value="SELECT SUM(amount) AS total_amount FROM orders WHERE transaction_type = 'Expense' and financial_yaer=:year", nativeQuery=true)
	public BigDecimal findYearlyTotalExpense(@Param("year") String year);

	public TransactionsSummary findByDeletedAndFinancialYearAndQuarterAndTransactionTypeAndUser(boolean deleted, String year, String quarter, String transactionType, User user);

	public TransactionsSummary findByDeletedAndId(boolean deleted, Long id);

	public Page<TransactionsSummary> findAllByDeletedAndFinancialYearAndQuarterAndTransactionTypeOrderByName(boolean deleted, String year, String quarter, String transactionType, Pageable pageable);

	public List<TransactionsSummary> findAllByUser(User user);
}
