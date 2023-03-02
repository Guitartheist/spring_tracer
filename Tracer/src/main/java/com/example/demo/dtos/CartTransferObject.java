package com.example.demo.dtos;

import com.example.demo.models.Cart;
import com.example.demo.models.CartItem;

public class CartTransferObject {
	Cart cartData;
	CartItem[] cartItems;
	
	public CartTransferObject(Cart cartData, CartItem[] cartItems) {
		this.cartData = cartData;
		this.cartItems = cartItems;
	}
	
	public Cart getCartData() {
		return this.cartData;
	}
	public CartItem[] getCartItems() {
		return this.cartItems;
	}
	
	public void setCartData(Cart cartData) {
		this.cartData = cartData;
	}
	public void setCartItems(CartItem[] cartItems) {
		this.cartItems = cartItems;
	}
}
