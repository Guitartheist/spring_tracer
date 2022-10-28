package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.AppUser;
import com.example.demo.services.AuthService;
import com.example.demo.services.UserService;
import com.example.demo.util.JwtTokenUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	AuthenticationManager authenticationManager;
	JwtTokenUtil jwtTokenUtil;
	AuthService authService;
	UserService userService;
	PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserController(AuthenticationManager authenticationManager,
			UserService userService,
			JwtTokenUtil jwtTokenUtil,
			AuthService authService,
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.authService = authService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping("/profile/{username}")
	@ResponseBody
	public AppUser getUser(@PathVariable("username") String username, Authentication auth) {
		int authorizedID = (auth!=null) ? userService.findUserByUsername(auth.getName()).getId() : 0;
		int requestedID = (userService.findUserByUsername(username)!=null) ? userService.findUserByUsername(username).getId() : -1;
		
		if (requestedID < 0)
			return null;
		
		AppUser ret = userService.findUserByUsername(username);
		
		// Strip privileged info from user object if the requesting user should not be able to receive it
		if (authorizedID!=requestedID) {
			ret.setEmail("");
		}
		
		return ret;
	}
	
	@PostMapping("/register")
	@ResponseBody
	public AppUser register(@RequestBody AppUser u) {
		u.setPassword( passwordEncoder.encode(u.getPassword()) );
		return userService.registerUser(u);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AppUser> login(@RequestBody AppUser u) {
		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword()));

			User user = (User) authenticate.getPrincipal();
			AppUser retUser = userService.findUserByUsername(user.getUsername());
			retUser.setPassword(null);
			
			String token = jwtTokenUtil.generateAccessToken(user);
		
			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token)
					.body(retUser);
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
