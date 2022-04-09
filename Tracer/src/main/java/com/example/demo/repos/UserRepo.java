package com.example.demo.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.AppUser;

@Repository
public interface UserRepo extends CrudRepository<AppUser, Integer> {
	AppUser findUserByUsername(String username);
	AppUser findUserByEmail(String email);
	
	@Query("from AppUser u where u.username = :username and u.password = :password")
	AppUser findUserByUsernameAndPassword(String username, String password);
}
