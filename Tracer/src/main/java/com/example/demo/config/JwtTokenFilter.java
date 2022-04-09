package com.example.demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.models.AppUser;
import com.example.demo.repos.UserRepo;
import com.example.demo.util.JwtTokenUtil;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	private final JwtTokenUtil jwtTokenUtil;
	private final UserRepo userRepo;

	@Autowired
	public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserRepo userRepo) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userRepo = userRepo;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Get authorization header and validate
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// Get jwt token and validate
		final String token = header.split(" ")[1].trim();
		if (!jwtTokenUtil.validate(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		// Get user identity and set it on the spring security context
		AppUser tokenUser = userRepo.findUserByUsername(jwtTokenUtil.getUsername(token));
		if (tokenUser == null) {
			filterChain.doFilter(request, response);
			return;
		}

		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		if ("admin".equals(tokenUser.getUsername()))
			authorities.add(Role.ADMIN);
		UserDetails userDetails = new User(tokenUser.getUsername(), tokenUser.getPassword(), authorities);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails == null ? Arrays.asList() : userDetails.getAuthorities());

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}
}
