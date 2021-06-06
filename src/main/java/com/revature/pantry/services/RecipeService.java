package com.revature.pantry.services;

import com.revature.pantry.exceptions.InvalidRecipeException;
import com.revature.pantry.models.Recipe;
import com.revature.pantry.repos.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

	private RecipeRepository recipeRepository;

	@Autowired
	public RecipeService(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	public Recipe findById(int id) {
		return recipeRepository.findById(id).orElseThrow(InvalidRecipeException::new);
	}

	public Recipe findByUrl(String url) {
		return recipeRepository.findByUrl(url).orElseThrow(InvalidRecipeException::new);
	}

	public Recipe save(Recipe recipe) {
		return recipeRepository.save(recipe);
	}

	public List<Recipe> saveAll(List<Recipe> recipes) {
		return recipeRepository.saveAll(recipes);
	}

	public boolean isRecipeValid(Recipe recipe) {
		return true;
	}

}
