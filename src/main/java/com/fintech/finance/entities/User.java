package com.fintech.finance.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Audited
@Entity(name="user")
@AuditTable(value="zzz_user")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {
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
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="mobile", nullable=false)
	private String mobile;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="user_name", nullable=false, unique=true)
	private String username;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="role", nullable=false)
	@Enumerated(EnumType.STRING)																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		
    private Role role;

	@Column(name="active", nullable=false)
    private boolean active = true;																																							
																																						
}
