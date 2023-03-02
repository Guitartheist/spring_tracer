package com.example.demo.dtos;

public class CartItemId {
	private int cartItemId;
	
	public CartItemId() {
		this.cartItemId = -1;
	}
	
	public CartItemId(int cartItemId) {
		this.cartItemId = cartItemId;
	}
	
	public int getCartItemId() {
		return this.cartItemId;
	}
	
	public void setCartItemId(int cartItemId) {
		this.cartItemId = cartItemId;
	}	
}
