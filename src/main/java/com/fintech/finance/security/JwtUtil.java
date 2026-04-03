package com.fintech.finance.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final Key key = Keys.hmacShaKeyFor("mysecretkeymysecretkeymysecretkey".getBytes());
	
	public String generateToken(String username, String role) {
	    return Jwts.builder()
	            .setSubject(username)
	            .claim("role", role)
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
	            .signWith(key)
	            .compact();
	}
	
	 public String extractUsername(String token) {
	        return getClaims(token).getSubject();
	    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }
    
    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
