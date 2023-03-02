package com.example.demo.controllers;

import java.util.Optional;
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
import com.example.demo.dtos.CartTransferObject;
import com.example.demo.models.Cart;
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
	
	@GetMapping("/find/user/{userId}")
	public ResponseEntity<CartTransferObject> findCartByUserId(
		@PathVariable("userId") int userId
	) {
		if (userId != -1) {
			try {
				UUID cartId = cartService.findCartIdByUserId(userId);
				try {
					Cart userCart = cartService.findCart(cartId).get();
					CartItem[] cartItems = cartService.findCartItemsByCartId(cartId);
					CartTransferObject cartToTransfer = new CartTransferObject(userCart, cartItems) ;					
					return ResponseEntity.ok().body(cartToTransfer);
				} catch (Exception e) {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
		System.out.println("Cart not found");
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/find/cart/{cartId}")
	public ResponseEntity<CartTransferObject> findCartByCartId(
		@PathVariable("cartId") UUID cartId
	) {
		if (cartId != null) {
			try {
				Cart userCart = cartService.findCart(cartId).get();
				CartItem[] cartItems = cartService.findCartItemsByCartId(cartId);
				return ResponseEntity.ok().body( new CartTransferObject(userCart, cartItems) );
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
		System.out.println("Cart not found");
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("delete/cart/{cartId}")
	public ResponseEntity<CartItem[]> deleteCart(
		@PathVariable("cartId") UUID cartId
	) {
		if (cartId != null) {
			try {
				cartService.deleteCartByCartId(cartId);
				CartItem[] cartItems = cartService.deleteCartItemsByCartId(cartId);
				return ResponseEntity.ok().body(cartItems); 
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
		System.out.println("Unable to delete cart. No cart Id");
		return ResponseEntity.notFound().build();		
	}
		
	
	@GetMapping("/find/items/user/{userId}")
	public ResponseEntity<CartItem[]> findCartItemsByUserId(
		@PathVariable("userId") int userId
	) {
		if (userId != -1) {
			try {		
				CartItem[] userCart = cartService.findCartItemsByUserId(userId);
				if (userCart != null)
					System.out.println(userCart);
					return ResponseEntity.ok().body(userCart);
			} catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
		System.out.println("Cart Items not found");
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/find/items/cart/{cartId}")
	public ResponseEntity<CartItem[]> findCartItemsByCartId(
		@PathVariable("cartId") UUID cartId
	) {
		if (cartId != null) {
			try {
				CartItem[] userCart = cartService.findCartItemsByCartId(cartId);
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
	
	@GetMapping("/delete/items/{cartId}")
	@ResponseBody
	public ResponseEntity<CartItem[]> deleteCartItemsByCartId(@PathVariable("cartId") UUID cartId) {		
		try {
			cartService.deleteCartByCartId(cartId);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		try {
			CartItem[] userCart = cartService.deleteCartItemsByCartId(cartId);
			if (userCart != null)
				return ResponseEntity.ok().body(userCart);
			else
				return ResponseEntity.notFound().build();
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	
	
	
	@PostMapping("/create/item")
	@ResponseBody
	public ResponseEntity<CartTransferObject> createCartItem(
		@RequestBody CartItem createdCartItem
	) {
		if (createdCartItem.getCartId() == null) {				// no cart id
			int userId = createdCartItem.getUserId();
			if (userId == -1) {									// user not logged in
				UUID createdCartId = UUID.randomUUID();
				createdCartItem.setCartId(createdCartId);
				String[] priceArray = createdCartItem.getPrice().split(".");
				cartService.saveCart(new Cart(createdCartId));
			} else {
				UUID cartId = cartService.findCartIdByUserId(createdCartItem.getUserId()); //  try to find cartId by userId
				if (cartId != null) {
					createdCartItem.setCartId(cartId);
				}
				else {
					UUID createdCartId = UUID.randomUUID();
					createdCartItem.setCartId(createdCartId);
					String[] priceArray = createdCartItem.getPrice().split("\n");	
					cartService.saveCart(new Cart(userId, createdCartId));
				}
			}
		}
		try {
			CartItem cartItem = cartService.createCartItem(createdCartItem); // save cart item
			// update cart price
			Optional<Cart> optionalCart = cartService.findCart(cartItem.getCartId());				
			Cart cartToUpdate = optionalCart.get();
			cartToUpdate.addToPrice(cartItem.getPrice());
			cartService.updateCart(cartToUpdate);					
			try {
				CartItem[] cartItems = cartService.findCartItemsByCartId(cartItem.getCartId());
				CartTransferObject cartTransferObject = new CartTransferObject(cartToUpdate, cartItems);
				return ResponseEntity.ok().body(cartTransferObject);
			} catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}						
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/delete/item")
	@ResponseBody
	public ResponseEntity<Cart> deleteCartItem(
		@RequestBody CartItemId cartItemId
	) {	
		try {
			CartItem cartItem = cartService.findCartItem(cartItemId.getCartItemId()).get();
			try {
				cartService.deleteCartItem(cartItemId.getCartItemId());
			} catch (Exception e) {
				System.out.println("LOG THIS: Failed to delete cart item");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			try {
				Optional<Cart> optionalCart = cartService.findCart(cartItem.getCartId());							
				try {
					Cart cartToUpdate = optionalCart.get();
					cartToUpdate.subtractFromPrice(cartItem.getPrice());
					cartService.updateCart(cartToUpdate);		
					return ResponseEntity.ok().body(cartToUpdate);
				} catch (Exception e) {
					System.out.println("LOG THIS: Failed to update cart");
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
				}
			} catch (Exception e) {
				System.out.println("LOG THIS: Failed to find cart");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		} catch (Exception e) {
			System.out.println("LOG THIS: Failed to locate cart item");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}			
	}
}
