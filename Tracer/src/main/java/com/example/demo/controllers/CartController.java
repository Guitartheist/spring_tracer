package com.example.demo.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.CartItemId;
import com.example.demo.models.CartItem;
import com.example.demo.services.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	//RestTemplate restTemplate;
	CartService cartService;
	
	@Autowired
	public CartController(
		//RestTemplate restTemplate
		CartService cartService
	){
		//this.restTemplate = restTemplate;
		this.cartService = cartService;
	}

	@PostMapping("/create/item")
	@ResponseBody
	public ResponseEntity<CartItem[]> createCartItem(
		@RequestBody CartItem createdCartItem
	) {
		if (createdCartItem.getCartId() == null) {
			int userId = createdCartItem.getUserId();
			if (userId != -1) {
				UUID cartId = cartService.findCartIdByUserId(createdCartItem.getUserId());
				if (cartId != null) {
					System.out.println("1111AAAA");
					createdCartItem.setCartId(cartId);
				}
				else {
					UUID createdCartId = UUID.randomUUID();
					createdCartItem.setCartId(createdCartId);					
				}
			} else {
				UUID createdCartId = UUID.randomUUID();
				createdCartItem.setCartId(createdCartId);				
			}
		}	
		try {
			CartItem cartItem = cartService.createCartItem(createdCartItem);		
			try {
				CartItem[] cart = cartService.findCartByCartId(cartItem.getCartId());
				if (cart != null)
					return ResponseEntity.ok().body(cart);
				else
					return ResponseEntity.notFound().build();
			} catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}		
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("find/{cartId}")
	@ResponseBody
	public ResponseEntity<CartItem[]> findCart(
		@PathVariable("cartId") UUID cartId
	) {
		System.out.println("Cart Id: "+cartId);		
		if (cartId != null) {
			try {
				CartItem[] userCart = cartService.findCartByCartId(cartId);
				if (userCart != null)
					System.out.println(userCart);
					return ResponseEntity.ok().body(userCart);
			} catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
		System.out.println("Cart not found");
		return ResponseEntity.notFound().build();
	}

	@GetMapping("delete/{cartId}")
	@ResponseBody
	public ResponseEntity<CartItem[]> deleteCartByCardId(@PathVariable("cartId") UUID cartId) {
		try {
			CartItem[] userCart = cartService.deleteCartByCartId(cartId);
			if (userCart != null)
				return ResponseEntity.ok().body(userCart);
			else
				return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/delete/item")
	@ResponseBody
	public ResponseEntity<Object> deleteCartItem(
		@RequestBody CartItemId cartItemId
	) {
		try {
			cartService.deleteCartItem(cartItemId.getCartItemId());
			return ResponseEntity.ok().build();
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}			
	}
}
