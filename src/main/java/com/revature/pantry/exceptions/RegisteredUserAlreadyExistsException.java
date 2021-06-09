package com.revature.pantry.exceptions;

public class RegisteredUserAlreadyExistsException extends RuntimeException{

    public RegisteredUserAlreadyExistsException() {
        super();
    }

    public RegisteredUserAlreadyExistsException(String message) {
        super(message);
    }
}
