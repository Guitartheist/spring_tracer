package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.AppUser;
import com.example.demo.repos.UserRepo;

@Service
public class UserService {
	private UserRepo userRepo;
	
	@Autowired
	public UserService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	public AppUser findUserById(int id) {
		Optional<AppUser> u = userRepo.findById(id);
		if (u.isPresent()) {
			return u.get();
		}
		return null;
	}
	
	public AppUser findUserByUsername(String username) {
		return userRepo.findUserByUsername(username);
	}
	
	public AppUser findUserByEmail(String email) {
		return userRepo.findUserByEmail(email);
	}
	
	public AppUser registerUser(AppUser u) {
		if (findUserByUsername(u.getUsername())!=null) {
			return null;
		}
		if (findUserByEmail(u.getEmail())!=null) {
			return null;
		}
		return userRepo.save(u);
	}
	
	public AppUser updateUser(AppUser u) {
		return userRepo.save(u);
	}
}
