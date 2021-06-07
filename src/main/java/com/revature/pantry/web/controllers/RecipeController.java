package com.revature.pantry.web.controllers;

import com.revature.pantry.exceptions.InvalidRecipeException;
import org.springframework.web.bind.annotation.*;
import com.revature.pantry.models.*;
import com.revature.pantry.services.*;
import com.revature.pantry.web.dtos.*;
import com.revature.pantry.web.security.Secured;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import java.util.*;
import java.util.regex.*;

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

	@GetMapping(value = "/find/id", produces = APPLICATION_JSON_VALUE)
	public Recipe findRecipeById(@RequestBody Integer id) {
		return recipeService.findById(id);
	}

	@GetMapping(value = "/find/url", produces = APPLICATION_JSON_VALUE)
	public Recipe findRecipeByUrl(@RequestBody String url) {
		return recipeService.findByUrl(url);
	}

	@PostMapping(value = "/save/one", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public Recipe save(@RequestBody Recipe recipe) {
		if (!recipeService.isRecipeValid(recipe)) {
			throw new InvalidRecipeException("Invalid recipe, could not save");
		}
		return recipeService.save(recipe);
	}

	@PostMapping(value = "/save/all", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public List<Recipe> saveAll(@RequestBody List<Recipe> recipes) {
		for (Recipe recipe : recipes) {
			if (!recipeService.isRecipeValid(recipe)) {
				throw new InvalidRecipeException("One of the Recipes you were trying to save was invalid");
			}
		}
		return recipeService.saveAll(recipes);
	}

  @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
  @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
  public List<RecipeDTO> searchRecipes(@RequestParam String q){
    edamamService.isIngredientValid(q);
    return edamamService.getRecipesFromEdamam(q);    
  }

}
