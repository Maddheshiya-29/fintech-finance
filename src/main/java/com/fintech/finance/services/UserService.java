package com.fintech.finance.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintech.finance.dtos.LoginResponseDto;
import com.fintech.finance.dtos.UserDto;
import com.fintech.finance.entities.User;
import com.fintech.finance.repositories.UserRepository;
import com.fintech.finance.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository repoUser;
    private final PasswordEncoder encoder;
    
    
	 public ResponseEntity<LoginResponseDto> authenticate(UserDto dto) {        
        authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        User user = repoUser.findByUsername(dto.getUsername()).get();
        
        LoginResponseDto responseDTO = new LoginResponseDto(true, "Login Successfull.", user.getUsername(), user.getName(), jwtUtil.generateToken(user.getUsername(), user.getRole().toString()), user.getRole().toString());
    	responseDTO.setEmail(user.getEmail());
    	responseDTO.setMobile(user.getMobile());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

}
