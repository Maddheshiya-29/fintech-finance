package com.fintech.finance.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintech.finance.dtos.ApiResponseDto;
import com.fintech.finance.dtos.UserDto;
import com.fintech.finance.entities.Role;
import com.fintech.finance.entities.TransactionsSummary;
import com.fintech.finance.entities.User;
import com.fintech.finance.repositories.TransactionsSummaryRepository;
import com.fintech.finance.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final TransactionsSummaryRepository repoTransSummary;
	private final UserRepository repoUser;
    private final PasswordEncoder encoder;
    private final UtilService util;


	public ResponseEntity<?> updateUser(Long id, UserDto dto) {
		boolean isPasswordUpdate = !util.isStringNullOrBlank(dto.getPassword()) && !util.isStringNullOrBlank(dto.getConfirmPassword()) && !util.isStringNullOrBlank(dto.getOldPassword());
		
	    if (isPasswordUpdate && !dto.getPassword().equals(dto.getConfirmPassword())) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("Password and confirm password mismatch").build());
	    }
	    Optional<User> optionalUser = repoUser.findById(id);
	    
	    if(!optionalUser.isPresent())
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.builder().status(false).message("User not found.").build());
		else {
			User user = optionalUser.get();
		    if (isPasswordUpdate && !encoder.matches(dto.getOldPassword(), user.getPassword())) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("Old password is incorrect").build());
		    }
		    if (isPasswordUpdate && encoder.matches(dto.getPassword(), user.getPassword())) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("New password cannot be same as old password").build());
		    }
		    if(dto.getActive()!=null)
		    	user.setActive(dto.getActive());
		    if(!util.isStringNullOrBlank(dto.getEmail()))
		    	user.setEmail(dto.getEmail().trim());
		    if(!util.isStringNullOrBlank(dto.getMobile()))
		    	user.setMobile(dto.getMobile().trim());
		    if(!util.isStringNullOrBlank(dto.getName())) {
		    	user.setName(dto.getName().trim());
		    	List<TransactionsSummary> summaries = repoTransSummary.findAllByUser(user);
		    	
		    	for(TransactionsSummary summary : summaries) {
		    		summary.setName(dto.getName().trim());
		    		repoTransSummary.save(summary);
		    	}
		    }
		    if(isPasswordUpdate)
		    	user.setPassword(encoder.encode(dto.getPassword()));
		    if(!util.isStringNullOrBlank(dto.getRole())) {
			    try {
			        Role role = Role.valueOf(dto.getRole().toUpperCase());
			        user.setRole(role);
			    } 
			    catch (IllegalArgumentException e) {
			        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseDto.builder().status(false).message("Invalid role").build());
			    }
		    }
		    repoUser.save(user);
		    return ResponseEntity.ok(ApiResponseDto.builder().status(true).message("User updated successfully").build());
	    }
	}

	public ResponseEntity<?> userByUsername(String username) {
		Optional<User> optionalUser = repoUser.findByUsername(username);
	    
	    if(!optionalUser.isPresent())
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDto.builder().status(false).message("User not found.").build());
		else {
			User user = optionalUser.get();
			
			UserDto dto = new UserDto();
			dto.setId(user.getId());
			dto.setUsername(user.getUsername());
			dto.setEmail(user.getEmail());
			dto.setMobile(user.getMobile());
			dto.setName(user.getName());
			dto.setRole(user.getRole().toString());
			return ResponseEntity.ok().body(dto);
		}
	}

	public ResponseEntity<?> users(boolean active, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

	    Page<User> usersPage = repoUser.findAllByActive(active, pageable);

	    List<UserDto> dtos = usersPage.getContent().stream().map(usr -> {
	        UserDto dto = new UserDto();
	        dto.setId(usr.getId());
	        dto.setName(usr.getName());
	        dto.setEmail(usr.getEmail());
	        dto.setMobile(usr.getMobile());
	        dto.setRole(usr.getRole().toString());
	        dto.setUsername(usr.getUsername());
	        return dto;
	    }).toList();

	    Map<String, Object> response = new HashMap<>();
	    response.put("users", dtos);
	    response.put("currentPage", usersPage.getNumber());
	    response.put("totalPages", usersPage.getTotalPages());
	    response.put("totalElements", usersPage.getTotalElements());

	    return ResponseEntity.ok().body(response);
	}
}
