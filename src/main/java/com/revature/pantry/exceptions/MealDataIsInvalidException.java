package com.revature.pantry.exceptions;

public class MealDataIsInvalidException extends RuntimeException{
    
    public MealDataIsInvalidException() { super();}
    
    public MealDataIsInvalidException(String message) { super(message); }
    
}
