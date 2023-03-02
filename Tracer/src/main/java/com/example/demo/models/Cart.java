package com.example.demo.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.dtos.currency.USD;

@Entity
@Table(name = "carts")
public class Cart {
	@Id
	@Column(unique = true, nullable = false)
	private UUID cartId;

	@Column(unique = false, nullable = false)
	private int userId;
	
	@Column(unique = false, nullable = false)
	private String costPreTotal = "0.0";
	
	public Cart() {
		this.cartId = null;
		this.userId = -1;
	}

	public Cart(int userId, UUID cartId) {
		this.cartId = cartId;		
		this.userId = userId;
	}
	
	public Cart(int userId) {
		this.cartId = null;		
		this.userId = userId;
	}

	public Cart(UUID cartId) {
		this.cartId = cartId;
		this.userId = -1;
	}
	
	public UUID getCartId() {
		return this.cartId;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public String getCostPreTotal() {
		return this.costPreTotal;
	}


	public void setCartId(UUID cartId) {
		this.cartId = cartId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setCostPreTotal(String costPreTotal) {
		this.costPreTotal = costPreTotal;
	}
	
	public void addToPrice(String priceToAdd) {
		priceToAdd = priceToAdd.strip().replace("$", "");
		USD priceObject = new USD(this.costPreTotal);
		this.costPreTotal = priceObject.addToPrice(priceToAdd);		
	}
	public void subtractFromPrice(String priceToSubtract) {
		priceToSubtract = priceToSubtract.strip().replace("$", "");
		USD priceObject = new USD(this.costPreTotal);
		this.costPreTotal = priceObject.subtractFromPrice(priceToSubtract);
	}
}
