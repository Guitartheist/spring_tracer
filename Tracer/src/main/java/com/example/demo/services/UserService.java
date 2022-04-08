package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repos.UserRepo;

@Service
public class UserService {
	private UserRepo userRepo;
	
	@Autowired
	public UserService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	public User findUserById(int id) {
		Optional<User> u = userRepo.findById(id);
		if (u.isPresent()) {
			return u.get();
		}
		return null;
	}
	
	public User findUserByUsername(String username) {
		return userRepo.findUserByUsername(username);
	}
	
	public User findUserByEmail(String email) {
		return userRepo.findUserByEmail(email);
	}
	
	public User registerUser(User u) {
		return null;
	}
	
	public User updateUser(User u) {
		return userRepo.save(u);
	}
	
	public User loginUser(User u) {
		return userRepo.findUserByUsernameAndPassword(u.getUsername(), u.getPassword());
	}
}
