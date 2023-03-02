package com.example.demo.repos;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.CartItem;

@Repository
public interface CartItemRepo extends CrudRepository<CartItem, Integer> {
	CartItem[] findCartItemsByUserId(int userId);
	CartItem[] findCartItemsByCartId(UUID cartId);

	CartItem[] deleteCartItemsByCartId(UUID cartId); 
}
