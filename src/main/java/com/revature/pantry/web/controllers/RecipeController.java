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


	@GetMapping(value = "/id/{id}", produces = APPLICATION_JSON_VALUE)
	public Recipe findRecipeById(@PathVariable Integer id) {
		return recipeService.findById(id);
	}

//	@GetMapping(value = "/url/{url}", produces = APPLICATION_JSON_VALUE)
//	public Recipe findRecipeByUrl(@PathVariable String url) {
//		return recipeService.findByUrl(url);
//	}


	@Deprecated
	@PostMapping(value = "/save/one", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public Recipe save(@RequestBody Recipe recipe) {
		return recipeService.save(recipe);
	}

	@Deprecated
	@PostMapping(value = "/save/all", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public List<Recipe> saveAll(@RequestBody List<Recipe> recipes) {
		return recipeService.saveAll(recipes);
	}


	@GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
	@Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
	public List<RecipeDTO> searchRecipes(@RequestParam String q){
	edamamService.isIngredientValid(q);
	return edamamService.getRecipesFromEdamam(q);
	}

}
