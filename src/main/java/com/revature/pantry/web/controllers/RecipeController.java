package com.revature.pantry.web.controllers;

import com.revature.pantry.models.*;
import com.revature.pantry.services.*;
import com.revature.pantry.web.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/search")
public class RecipeController {

    private EdamamService edamamService;

    @Autowired
    public RecipeController(EdamamService edamamService){
        this.edamamService = edamamService;
    }

    @GetMapping(value = "/recipes", produces = APPLICATION_JSON_VALUE)
    public List<RecipeDTO> searchRecipes(@RequestParam String q){
        edamamService.isIngredientValid(q);
        return edamamService.getRecipesFromEdamam(q);    }


}
