package com.revature.pantry.exceptions;

public class UserDataIsInvalid extends RuntimeException{
    
    public UserDataIsInvalid() { super();}
    
    public UserDataIsInvalid(String message) { super(message); }
    
}
