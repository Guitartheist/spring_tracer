package com.example.demo.repos;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.CartItem;

@Repository
public interface CartItemRepo extends CrudRepository<CartItem, Integer> {
	CartItem[] findCartByUserId(int userId);
	CartItem[] findCartByCartId(UUID cartId);
	//CartItem findCartItemByCartItemId(int cart_item_id);

	CartItem[] deleteCartByCartId(UUID cartId); 
	//CartItem deleteCartItemByCartItemId(int cart_item_id);	
}
