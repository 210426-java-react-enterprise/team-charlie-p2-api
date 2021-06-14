package com.revature.pantry.web.controllers;

import com.revature.pantry.exceptions.InvalidRecipeException;
import org.springframework.web.bind.annotation.*;
import com.revature.pantry.models.*;
import com.revature.pantry.services.*;
import com.revature.pantry.web.dtos.*;
import com.revature.pantry.web.security.Secured;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
  
	private EdamamService edamamService;
	private RecipeService recipeService;

	@Autowired
	public RecipeController(RecipeService recipeService, EdamamService edamamService) {
		this.recipeService = recipeService;
    this.edamamService = edamamService;
	}

	/**
	 * Takes in a search query and passes it along to the service to search for recipes
	 * @param q the search query
	 * @return a list of Recipes in the form of JSON
	 * @author Kevin Chang
	 */
	@GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
	//@Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
	public List<RecipeDTO> searchRecipes(@RequestParam String q){
	edamamService.isIngredientValid(q);
	return edamamService.getRecipesFromEdamam(q);
	}

}
