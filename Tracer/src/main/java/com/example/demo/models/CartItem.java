package com.example.demo.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "cart_items")
public class CartItem {
	@Id
	@GeneratedValue
	private int cartItemId;
	
	@Column(unique = false, nullable = false)
	private int userId;
	
	@Column(unique = false, nullable = false)
	private UUID cartId;
	
    @CreationTimestamp
    private LocalDateTime timeStamp;
	
	@Column(unique = false, nullable = false)
	String name;
	
	@Column(unique = false, nullable = false)
	String size;
	
	@Column(unique = false, nullable = false)
	String price;
	
	@Column(unique = false, nullable = false, columnDefinition="CLOB")
	boolean in_stock;

	@Lob @Basic(fetch=FetchType.LAZY)
	@Column(unique = false, nullable = false)
	private String selectedImage;

	@Column(unique = false, nullable = false)
	int imageWidthInPixels;

	@Column(unique = false, nullable = false)
	int imageHeightInPixels;
	
	@Column(unique = false, nullable = false)
	boolean rotated;
	
	@Column(unique = false, nullable = false)
	String prompt;
	
	@Column(unique = false, nullable = false)
	int productId;
	
	public CartItem() {
		
	}
	
	public int getCartItemId() { return this.cartItemId; }
	public int getUserId() { return this.userId; }
	public UUID getCartId() {
		return this.cartId;
	}
	public LocalDateTime getTimeStamp() { return this.timeStamp; }
	public String getName() {return this.name; }
	public String getSize() {return this.size; }
	public String getPrice() {return this.price; }
	public boolean getIn_stock() {return this.in_stock; }
	public String getSelectedImage() {return this.selectedImage; }
	public int getImageWidthInPixels() {return this.imageWidthInPixels; }
	public int getImageHeightInPixels() {return this.imageHeightInPixels; }
	public boolean getRotated() {return this.rotated; }
	public String getPrompt() { return this.prompt; }
	public int getProductId() {return this.productId; }

	public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }
	public void setUserId(int userId) { this.userId = userId; }
	public void setCartId(UUID cartId) { 
		this.cartId = cartId; 
	}
	public void setTimeStamp(LocalDateTime timeStamp) { 
        this.timeStamp = timeStamp;
	}
	public void setName(String name) { this.name = name; }
	public void setSize(String size) { this.size = size; }
	public void setPrice(String price) { this.price = price; }
	public void setIn_stock(boolean in_stock) { this.in_stock = in_stock; }
	public void setSelectedImage(String selectedImage) { this.selectedImage = selectedImage; }
	public void setImageWidthInPixels(int imageWidthInPixels) { this.imageWidthInPixels = imageWidthInPixels; }
	public void setImageHeightInPixels(int imageHeightInPixels) { this.imageHeightInPixels = imageHeightInPixels; }	
	public void setRotated(boolean rotated) { this.rotated = rotated; }
	public void setPrompt(String prompt) { this.prompt = prompt; }
	public void setProductId(int productId) { this.productId = productId; }

	public String toString() {
		return Integer.toString(cartItemId)+"\n"+
			   //cartId.toString()+"\n"+	
			   //timeStamp.toString()+"\n"+
			   name+"\n"+
			   size+"\n"+
			   price+"\n"+
			   Boolean.toString(in_stock)+"\n"+
			   selectedImage+"\n"+
			   Integer.toString(imageWidthInPixels)+"\n"+
			   Integer.toString(imageHeightInPixels)+"\n"+
			   prompt+"\n" +
			   Boolean.toString(rotated)+"\n"+
			   Integer.toString(productId)+"\n";
	}
}
