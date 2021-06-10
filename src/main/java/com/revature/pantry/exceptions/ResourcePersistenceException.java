package com.revature.pantry.exceptions;

public class ResourcePersistenceException extends RuntimeException{

    public ResourcePersistenceException() {
        super();
    }

    public ResourcePersistenceException(String message) {
        super(message);
    }
}
