package com.example.demo.repos;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Cart;

@Repository
public interface CartRepo extends CrudRepository<Cart, Integer> {
	int findUserIdByCartId(UUID cartId);
	
	UUID findCartIdByUserId(int userId);
}
