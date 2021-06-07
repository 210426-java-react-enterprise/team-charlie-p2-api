package com.revature.pantry.exceptions;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException() {

    }

    public InvalidRequestException(String message) {
        super(message);
    }
}
