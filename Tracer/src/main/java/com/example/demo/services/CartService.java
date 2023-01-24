package com.example.demo.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.CartItem;
import com.example.demo.repos.CartItemRepo;
import com.example.demo.repos.CartRepo;

@Service
public class CartService {
	private CartRepo cartRepo;
	private CartItemRepo cartItemRepo;
	
	@Autowired
	public CartService(CartRepo cartRepo,  CartItemRepo cartItemRepo) {
		this.cartRepo = cartRepo;
		this.cartItemRepo = cartItemRepo;
	}
	
	public UUID findCartIdByUserId(int userId) {
		return cartRepo.findCartIdByUserId(userId);
	}	
	public int findUserIdByCartId(UUID cartId) {
		return cartRepo.findUserIdByCartId(cartId);
	}
	
	public CartItem createCartItem(CartItem cartItem) {
		return cartItemRepo.save(cartItem);
	}
	
	public CartItem[] findCartByUserId(int userId) {
		return cartItemRepo.findCartByUserId(userId);
	}
	public CartItem[] findCartByCartId(UUID cartId) {
		return cartItemRepo.findCartByCartId(cartId);
	}
	//public CartItem findCartItem(int cart_item_id) {
		//return cartItemRepo.findCartItemByCartItemId(cart_item_id);
	//}
	
	public CartItem[] deleteCartByCartId(UUID cartId) {
		return cartItemRepo.deleteCartByCartId(cartId);
	}
	public void deleteCartItem(int cart_item_id) {
		cartItemRepo.deleteById(cart_item_id);
	}
}
