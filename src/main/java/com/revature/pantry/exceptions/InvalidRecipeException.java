package com.revature.pantry.exceptions;

public class InvalidRecipeException extends RuntimeException{

	public InvalidRecipeException() {
		super();
	}

	public InvalidRecipeException(String message) {
		super(message);
	}
}
