package com.revature.pantry.exceptions;

public class MealDataIsInvalid extends RuntimeException{
    
    public MealDataIsInvalid() { super();}
    
    public MealDataIsInvalid(String message) { super(message); }
    
}
