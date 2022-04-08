package com.example.demo.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
	User findUserByUsername(String username);
	User findUserByEmail(String email);
	
	@Query("from User u where u.username = :username and u.password = :password")
	User findUserByUsernameAndPassword(String username, String password);
}
