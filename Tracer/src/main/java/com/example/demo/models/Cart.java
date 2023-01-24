package com.example.demo.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "carts")
public class Cart {
	@Id
	@Column(unique = true, nullable = false)
	private int userId;
	
	@Column(unique = true, nullable = false)
	private UUID cartId;
}
