package com.revature.pantry.exceptions;

public class AuthenticationException extends Exception{

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
