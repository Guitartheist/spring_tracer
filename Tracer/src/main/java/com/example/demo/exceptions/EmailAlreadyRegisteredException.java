package com.example.demo.exceptions;

public class EmailAlreadyRegisteredException extends Exception {
	public EmailAlreadyRegisteredException(String email) {
		super("Email " + email + " is already registered.");
	}
}
