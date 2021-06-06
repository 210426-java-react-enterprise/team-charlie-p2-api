package com.revature.pantry.exceptions;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
