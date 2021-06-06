package com.revature.pantry.exceptions;

public class AuthorizationException extends Exception{

    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String message) {
        super(message);
    }
}