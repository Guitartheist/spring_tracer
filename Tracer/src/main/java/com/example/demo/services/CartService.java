package com.example.demo.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Cart;
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
	public Optional<Cart> findCart(UUID cartId) {
		return cartRepo.findById(cartId);
	}
	public Cart saveCart(Cart cart) {
		return cartRepo.save(cart);
	}
	public Cart updateCart(Cart cart) {
		cartRepo.deleteById(cart.getCartId());
		return cartRepo.save(cart);
	}
	public void deleteCart(Cart cart) {
		cartRepo.delete(cart);
	}
	public void deleteCartByCartId(UUID cartId) {
		cartRepo.deleteById(cartId);
	}
	
	public CartItem createCartItem(CartItem cartItem) {
		return cartItemRepo.save(cartItem);
	}
	public Optional<CartItem> findCartItem(int itemId) {
		return cartItemRepo.findById(itemId);
	}
	public CartItem[] findCartItemsByUserId(int userId) {
		return cartItemRepo.findCartItemsByUserId(userId);
	}
	public CartItem[] findCartItemsByCartId(UUID cartId) {
		return cartItemRepo.findCartItemsByCartId(cartId);
	}
	public CartItem[] deleteCartItemsByCartId(UUID cartId) {
		return cartItemRepo.deleteCartItemsByCartId(cartId);
	}
	public void deleteCartItem(int cart_item_id) {
		cartItemRepo.deleteById(cart_item_id);
	}
}
