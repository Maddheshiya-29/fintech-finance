package com.fintech.finance.services;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fintech.finance.entities.User;
import com.fintech.finance.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilService {

	private final UserRepository repoUser;
	
	public Pattern patEmail = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
	public Pattern patMobile = Pattern.compile("^\\d{10}$");
	
	public DateTimeFormatter dateLT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public boolean isStringNullOrBlank(String text) {
		if(text==null || text.isBlank())
			return true;
		else
			return false;
	}
	
	public User getLoggedInUser() {
        return repoUser.findByActiveAndUsername(false, SecurityContextHolder.getContext().getAuthentication().getName()).get();
    }
}
