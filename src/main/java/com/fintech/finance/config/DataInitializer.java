package com.fintech.finance.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.fintech.finance.entities.Role;
import com.fintech.finance.entities.User;
import com.fintech.finance.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository repoUser;
    private final BCryptPasswordEncoder encoder;

    @PostConstruct
    public void initAdmin() {

        if (repoUser.findByUsername("admin").isEmpty()) {

            User admin = new User();
            admin.setUsername("admin");
            admin.setName("Admin User");
            admin.setEmail("admin@test.com");
            admin.setMobile("9999999999");
            admin.setActive(true);
            admin.setPassword(encoder.encode("admin@123"));
            admin.setRole(Role.ADMIN);

            repoUser.save(admin);

            System.out.println("✅ Default admin created");
        }
    }
}
