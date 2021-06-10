package com.revature.pantry.exceptions;

public class UserDataIsInvalidException extends RuntimeException{
    
    public UserDataIsInvalidException() { super();}
    
    public UserDataIsInvalidException(String message) { super(message); }
    
}
