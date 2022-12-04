package com.example.demo.controllers;

import java.util.ArrayList;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AppUserListEntry;
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
	
	@GetMapping("/token")
	@ResponseBody
	public AppUser getUsernameFromToken(Authentication auth) {
		if (auth!=null && auth.isAuthenticated()) {
			AppUser u = new AppUser();
			u.setEmail("");
			u.setUsername(auth.getName());
			return u;
		}
		return null;
	}
	
	@GetMapping("/logout")
	@ResponseBody
	public void logoutUser(Authentication auth) {
		
	}
	
	@GetMapping("/all")
	@ResponseBody
	public ArrayList<AppUserListEntry> getAllUsers() {
		ArrayList<AppUserListEntry> users = new ArrayList<>();
		for (AppUser u:userService.findAllUsers()) {
			users.add(new AppUserListEntry(u.getUsername(), u.getProfilePreviewImage()));
		}
		return users;
	}
	
	@GetMapping("/{pageNo}/{pageSize}")
	public ArrayList<AppUserListEntry> getPaginatedUsers(@PathVariable int pageNo, 
            @PathVariable int pageSize) {
		ArrayList<AppUserListEntry> users = new ArrayList<>();
		for (AppUser u:userService.findPaginated(pageNo, pageSize)) {
			users.add(new AppUserListEntry(u.getUsername(), u.getProfilePreviewImage()));
		}
		return users;
	}
	
	@GetMapping("/profile/{username}")
	@ResponseBody
	public AppUser getUser(@PathVariable("username") String username, Authentication auth) {
		int authorizedID = (auth!=null) ? userService.findUserByUsername(auth.getName()).getId() : 0;
		int requestedID = (userService.findUserByUsername(username)!=null) ? userService.findUserByUsername(username).getId() : -1;
		
		if (requestedID < 0)
			return null;
		
		AppUser ret = userService.findUserById(requestedID);
		
		// Strip privileged info from user object if the requesting user should not be able to receive it
		if (authorizedID!=requestedID) {
			ret.setEmail("");
		}
		
		return ret;
	}
	
	@PutMapping("/profile")
	@ResponseBody
	public AppUser updateUser(@RequestBody AppUser u, Authentication auth) {
		int authorizedID = (auth!=null) ? userService.findUserByUsername(auth.getName()).getId() : 0;
		int requestedID = (userService.findUserByUsername(u.getUsername())!=null) ? userService.findUserByUsername(u.getUsername()).getId() : -1;
		
		if (requestedID < 1)
			return null;
		
		AppUser ret = userService.findUserById(requestedID);
		
		// Do nothing if the editing user is not authorized to make this edit
		if (authorizedID!=requestedID) {
			return null;
		}
		// Update user information
		else {
			// If user isn't changing their password, don't overwrite their password with a blank string
			if (u.getPassword().length() < 1 ) {
				u.setPassword(ret.getPassword());
			}
			// If the user is changing their password, apply the encoder
			else {
				u.setPassword( passwordEncoder.encode( u.getPassword()) );
			}
			// Same for the rest of their info, if it's blank or not provided, don't overwrite it
			if (u.getUsername().length() < 1) {
				u.setUsername(ret.getUsername());
			}
			if (u.getEmail().length() < 1) {
				u.setEmail(ret.getEmail());
			}
			// Don't overwrite profile image with a blank, if a profile image was passed, base 64 encode it
			if (u.getProfileImage().length() < 1) {
				u.setProfileImage(ret.getProfileImage());
			}
			u.setId(requestedID);
		}
		
		return userService.updateUser(u);
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
