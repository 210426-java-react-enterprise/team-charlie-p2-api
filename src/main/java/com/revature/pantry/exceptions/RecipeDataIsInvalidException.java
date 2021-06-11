package com.revature.pantry.exceptions;

public class RecipeDataIsInvalidException extends RuntimeException{
    
    public RecipeDataIsInvalidException() { super();}
    
    public RecipeDataIsInvalidException(String message) { super(message); }
    
}
