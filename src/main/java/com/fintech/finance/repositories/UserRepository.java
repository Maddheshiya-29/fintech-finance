package com.fintech.finance.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintech.finance.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByUsername(String username);

}
