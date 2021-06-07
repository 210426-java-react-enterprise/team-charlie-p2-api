package com.revature.pantry.aspects;


import com.revature.pantry.exceptions.*;
import com.revature.pantry.web.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;

import java.util.*;

@RestControllerAdvice
public class ExceptionHandlerAspect {

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public void handleResourceNotFoundException() { }

    //Returns a DTO ErrorMessage containing JSON of certain information that can be parsed and handled in the UI
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
    public void handleAuthenticationException() { }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public void handleAuthorizationException() { }
}
