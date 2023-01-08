package com.example.demo.exceptions;

public class UsernameAlreadyRegisteredException extends Exception {
	public UsernameAlreadyRegisteredException(String username) {
		super("Username " + username + " already exists.");
	}
}
