package com.example.demo.util;

import io.jsonwebtoken.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {
	private final String jwtSecret;

	private final String jwtIssuer;
	private static HashMap<String, ArrayList<String>> loggedOutTokens = new HashMap<>();

	JwtTokenUtil(@Autowired Environment environment) {
		this.jwtSecret = environment.getProperty("com.example.demo.secret");
		this.jwtIssuer = environment.getProperty("com.example.demo.jwtissuer");
	}
	
	public void logout(String token) {
		loggedOutTokens.get(getUsername(token)).add(token);
	}

	public String generateAccessToken(User user) {
		purgeExpired(user.getUsername());
		return Jwts.builder().setSubject(user.getUsername()).setIssuer(jwtIssuer).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
				.signWith(SignatureAlgorithm.HS512, jwtSecret) // Signed with the secret
				.compact();
	}

	public String getUsername(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	public Date getExpirationDate(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return claims.getExpiration();
	}
	
	public void purgeExpired(String user) {
		for (String token:loggedOutTokens.get(user)) {
			try {
				Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);	
			} catch (ExpiredJwtException ex) {
				loggedOutTokens.get(user).remove(token);
			}
		}
	}

	public boolean validate(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			if (loggedOutTokens.get(getUsername(token)).contains(token)) {
				System.err.println("Logged out JWT");
				return false;
			}
			return true;
		} catch (SignatureException ex) {
			System.err.println("Invalid JWT signature");
			System.err.println(ex.getMessage());
		} catch (MalformedJwtException ex) {
			System.err.println("Malformed JWT");
			System.err.println(ex.getMessage());
		} catch (ExpiredJwtException ex) {
			System.err.println("Expired JWT signature");
			loggedOutTokens.remove(token);
			System.err.println(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			System.err.println("JWT claims string is empty");
			System.err.println(ex.getMessage());
		}
		return false;
	}
}