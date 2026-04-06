package com.fintech.finance.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fintech.finance.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByUsername(String username);
	
	public Page<User> findAllByActive(boolean active, Pageable pageable);

	public User findByActiveAndId(boolean active, Long id);
	
	public Optional<User> findByActiveAndUsername(boolean active, String username);


}
