package com.revature.pantry.aspects;


import com.revature.pantry.exceptions.*;
import com.revature.pantry.web.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;

import java.util.*;

/**
 * ExceptionHandlerAspect
 *
 * Aspect for handling our custom exceptions. Every custom exception that makes it to a controller will be handled here, sending back a relevant response code and error message.
 *
 * @author Austin Knauer
 * @author Oswaldo Castillo
 */
@RestControllerAdvice
public class ExceptionHandlerAspect {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidIngredientException.class)
    public ErrorMessage handleInvalidIngredientException(InvalidIngredientException e, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );

        return message;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ErrorMessage handleAuthenticationException(AuthenticationException e, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );

        return message;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public ErrorMessage handleAuthorizationException(AuthorizationException e, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );

        return message;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourcePersistenceException.class)
    public ErrorMessage handleResourcePersistenceException(ResourcePersistenceException e, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );

        return message;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvalidRecipeException.class)
    public ErrorMessage handleInvalidRecipeException(InvalidRecipeException e, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );

        return message;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    public ErrorMessage handleInvalidRequestException(InvalidRequestException e, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );

        return message;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MealDataIsInvalidException.class)
    public ErrorMessage handleMealDataIsInvalidException(MealDataIsInvalidException e, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );

        return message;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserDataIsInvalidException.class)
    public ErrorMessage handleUserDataIsInvalidException(UserDataIsInvalidException e, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );

        return message;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage handleMethodArgumentNotValidException (MethodArgumentNotValidException e, WebRequest request) {
        String errorMessage = "";
        if(e.getMessage().contains("^[a-zA-Z0-9_.-]*$")) {
            errorMessage = errorMessage.concat("The username cannot contain any special characters. ");
        }
        if(e.getMessage().contains("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$")){
            errorMessage = errorMessage.concat("The password must be at least 8 characters and include at least 1 lowercase letter, " +
                    "1 uppercase letter, 1 special character (#?!@$ %^&*-) and 1 number. ");
        }
        if(e.getMessage().contains("email")) {
            errorMessage = errorMessage.concat("The email must be a valid email. (ex: something@mail.com)");
        }
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                errorMessage,
                request.getDescription(false)
        );
        return message;
    }
}
