package com.example.demo.dtos;

public class CartItemId {
	
	private int cartItemId;
	
	public CartItemId() {
		this.cartItemId = -1;
	}
	
	public CartItemId(int itemId) {
		this.cartItemId = itemId;
	}
	
	public int getCartItemId() { return this.cartItemId; }
	public void setCartItemId(int itemId) { this.cartItemId = itemId; }
}
