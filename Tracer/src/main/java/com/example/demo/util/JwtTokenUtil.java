package com.example.demo.util;

import io.jsonwebtoken.*;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {
	private final String jwtSecret;

	private final String jwtIssuer;

	JwtTokenUtil(@Autowired Environment environment) {
		this.jwtSecret = environment.getProperty("com.example.demo.secret");
		this.jwtIssuer = environment.getProperty("com.example.demo.jwtissuer");
	}

	public String generateAccessToken(User user) {
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

	public boolean validate(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			System.err.println("Invalid JWT signature");
			System.err.println(ex.getMessage());
		} catch (MalformedJwtException ex) {
			System.err.println("Malformed JWT");
			System.err.println(ex.getMessage());
		} catch (ExpiredJwtException ex) {
			System.err.println("Expired JWT signature");
			System.err.println(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			System.err.println("JWT claims string is empty");
			System.err.println(ex.getMessage());
		}
		return false;
	}
}