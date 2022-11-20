package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	
	public ArrayList<AppUser> findAllUsers() {
		return (ArrayList<AppUser>) userRepo.findAll();
	}
	
	public AppUser findUserById(int id) {
		Optional<AppUser> u = userRepo.findById(id);
		if (u.isPresent()) {
			return u.get();
		}
		return null;
	}
	
    public List<AppUser> findPaginated(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<AppUser> pagedResult = userRepo.findAll(paging);

        return pagedResult.toList();
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
