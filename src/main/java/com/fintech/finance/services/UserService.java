package com.fintech.finance.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintech.finance.dtos.ApiResponseDto;
import com.fintech.finance.dtos.LoginDto;
import com.fintech.finance.dtos.LoginResponseDto;
import com.fintech.finance.dtos.UserDto;
import com.fintech.finance.entities.Role;
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
    
    
	 public ResponseEntity<LoginResponseDto> authenticate(LoginDto dto) {        
        authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        User user = repoUser.findByActiveAndUsername(true, dto.getUsername()).get();
        
        LoginResponseDto responseDTO = new LoginResponseDto(true, "Login Successfull.", user.getUsername(), user.getName(), jwtUtil.generateToken(user.getUsername(), user.getRole().toString()),
        														user.getRole().toString());
    	responseDTO.setEmail(user.getEmail());
    	responseDTO.setMobile(user.getMobile());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


	 public ResponseEntity<ApiResponseDto> usernameExists(String username) {
		 Optional<User> user = repoUser.findByUsername(username);
		 
		 if(!user.isPresent())
		     return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDto.builder().status(true).message("Username permitted !!").build());
		 else
		     return ResponseEntity.status(HttpStatus.OK).body(ApiResponseDto.builder().status(false).message("Username already exists !!").build());	        
	 }


	 public ResponseEntity<?> register(UserDto dto) {

	    if (!dto.getPassword().equals(dto.getConfirmPassword())) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("Password and confirm password mismatch").build());
	    }
	    Optional<User> existingUser = repoUser.findByUsername(dto.getUsername().trim());

	    if (existingUser.isPresent()) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponseDto.builder().status(false).message("Username already exists").build());
	    }

	    User user = new User();
	    user.setUsername(dto.getUsername().trim());
	    user.setActive(true);
	    user.setEmail(dto.getEmail().trim());
	    user.setMobile(dto.getMobile().trim());
	    user.setName(dto.getName().trim());
	    user.setPassword(encoder.encode(dto.getPassword()));

	    try {
	        Role role = Role.valueOf(dto.getRole().toUpperCase());
	        user.setRole(role);
	    } 
	    catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("Invalid role").build());
	    }
	    repoUser.save(user);
	    return ResponseEntity.ok(ApiResponseDto.builder().status(true).message("User registered successfully").build());
	}

	 public ResponseEntity<?> roles() {
		 List<String> roles = Arrays.stream(Role.values()).map(Enum::name).toList();
		 UserDto dto = new UserDto();
		 dto.setRoles(roles);
	     return ResponseEntity.status(HttpStatus.OK).body(dto);	        
	 }
}
