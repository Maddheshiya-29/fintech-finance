package com.fintech.finance.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Entity(name="transactions_summary")
@AuditTable(value="zzz_transactions_summary")
@EntityListeners(AuditingEntityListener.class)
public class TransactionsSummary implements Serializable {
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
	
	@Column(name="quarter", nullable=false)
	private String quarter;
	
	@Column(name="financial_year", nullable=false)
	private String financialYear;
	
	@Column(name="transaction_type", nullable=false)
	private String transactionType;
	
	@Digits(integer=8, fraction=2)
	@Column(name="amount", nullable=true)
	private BigDecimal amount = new BigDecimal(0.00);
	
	@Column(name="name", nullable=false)
	private String name;
	
	@ManyToOne
	@NotAudited
    @JoinColumn(name="user", nullable=false)
	private User user;

}
