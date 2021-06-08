package com.revature.pantry.exceptions;

public class RecipeDataIsInvalid extends RuntimeException{
    
    public RecipeDataIsInvalid() { super();}
    
    public RecipeDataIsInvalid(String message) { super(message); }
    
}
