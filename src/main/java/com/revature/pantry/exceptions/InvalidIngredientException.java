package com.revature.pantry.exceptions;

public class InvalidIngredientException extends RuntimeException{

    public InvalidIngredientException() {
        super();
    }

    public InvalidIngredientException(String message) {
        super(message);
    }
}
