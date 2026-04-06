package com.fintech.finance.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;
import lombok.Data;

@Data
@Audited
@Entity(name="transactions_detail")
@AuditTable(value="zzz_transactions_detail")
@EntityListeners(AuditingEntityListener.class)
public class TransactionsDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    @CreatedBy
    @Column(name="created_by", nullable=true)
    private String createdBy;

    @CreatedDate
    @Column(name="created_date", nullable=true)
    private LocalDateTime createdDate;
    
    @LastModifiedBy
    @Column(name="modified_by", nullable=true)
    private String modifiedBy;

    @LastModifiedDate
    @Column(name="modified_date", nullable=true)
    private LocalDateTime modifiedDate;

	@Column(name="deleted", nullable=false)
	private Boolean deleted=false;
	
    @Column(name="transaction_date", nullable=false)
    private LocalDate transactionDate;
	
    @Column(name="description", nullable=false)
    private String description;
    
    @Digits(integer=8, fraction=2)
	@Column(name="amount", nullable=true)
	private BigDecimal amount = new BigDecimal(0.00);
    
    @Column(name="category", nullable=false)
    private String category;
    
    @ManyToOne
	@NotAudited
    @JoinColumn(name="transactions_summary", nullable=false)
	private TransactionsSummary transactionsSummary;

}
