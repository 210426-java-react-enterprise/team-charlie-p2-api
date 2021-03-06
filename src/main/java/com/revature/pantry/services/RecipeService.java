package com.revature.pantry.services;

import com.revature.pantry.exceptions.InvalidRecipeException;
import com.revature.pantry.exceptions.RecipeDataIsInvalidException;
import com.revature.pantry.exceptions.UserDataIsInvalidException;
import com.revature.pantry.models.Recipe;
import com.revature.pantry.repos.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Deprecated
@Service
public class RecipeService {
    
    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository){this.recipeRepository = recipeRepository;}
	
	
	public Recipe findById(int id) {
        return recipeRepository.findById(id).orElseThrow(InvalidRecipeException::new);
	}
	
//	public Recipe findByUrl(String url) {
//		return recipeRepository.findByUrl(url).orElseThrow(InvalidRecipeException::new);
//	}
	
	public Recipe save(Recipe recipe) {
        isRecipeValid(recipe);
        return recipeRepository.save(recipe);
	}
	
	public List<Recipe> saveAll(List<Recipe> recipes) {
       try{
           for (Recipe recipe: recipes) {
               isRecipeValid(recipe);
           }
       }catch (RecipeDataIsInvalidException e){
           e.printStackTrace();
       }
       
        return recipeRepository.saveAll(recipes);
	}
	


    /**
     * This method is responsible for validate the recipe data inputs against the app constraints
     *
     * @param recipe - Recipe data to be audit
     * @return - TRUE if data passed all the constraints
     * @throws UserDataIsInvalidException - Return this exception if the User Data not satisfied the constraints
     */
    public boolean isRecipeValid(Recipe recipe) throws RecipeDataIsInvalidException {
    
        
        //Recipe cannot be null
        if(recipe == null){throw new RecipeDataIsInvalidException("Please provide a not null object");}
    
        try{
            //CALORIES
            //Not null or Empty
            isNullOrEmpty("Calories", String.valueOf(recipe.getCalories()));
            //Must contains numeric characters
            isPatternSatisfied("Calories",
                               "^[0-9]*$",
                               String.valueOf(recipe.getCalories()),
                               "It's not a numeric value");
        
            //YIELD
            isNullOrEmpty("Yield", Integer.toString(recipe.getYield()));
            //Must contains numeric characters
            isPatternSatisfied("Yield",
                               "^[0-9]*$",
                               String.valueOf(recipe.getYield()),
                               "It's not a numeric value");
        
            //URL
            // Not null or empty
            isNullOrEmpty("Url", recipe.getUrl());
            //url > Not null and its a valid url address (Address must contains <http://]> or <https://>)
            isPatternSatisfied("Url",
                               "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)",
                               recipe.getUrl(),
                               "Must be a valid url address");
        
        }catch(RecipeDataIsInvalidException e){
            throw e;
        }
    
        //Return this if passed all the constraints
        return true;
    }
    
    
    /**
     * This method is part of isRecipeValid
     *
     * @param field - that we want to evaluate
     * @param strToEval - the string value from the field to be evaluated
     * @throws UserDataIsInvalidException - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isNullOrEmpty(String field,String strToEval) throws RecipeDataIsInvalidException {
        
        //Evaluates if the string passed is null or empty
        Predicate<String> isNullOrEmptyPredicate = str -> (str == null || str.trim().isEmpty());
        
        if(isNullOrEmptyPredicate.test(strToEval)) { throw new RecipeDataIsInvalidException(field+ ": empty or null");}
        
    }
    
    /**
     * This method is part of isRecipeValid
     *
     * @param field - that we want to evaluate
     * @param inputPattern - used to run the evaluation
     * @param strToEval - the string value from the field to be evaluated
     * @param exceptionMessage - Message in case of exception
     * @throws UserDataIsInvalidException - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isPatternSatisfied(String field, String inputPattern, String strToEval, String exceptionMessage) throws RecipeDataIsInvalidException {
        
        //Evaluates if the string passed satisfied the pattern passed
        BiPredicate<String, String> eval = ((str, pattern) -> {
            Pattern patternToCompile = Pattern.compile(pattern);
            Matcher patternValidation = patternToCompile.matcher(str);
            return patternValidation.matches();});
        
        if(!eval.test(strToEval, inputPattern)){ throw new RecipeDataIsInvalidException(field+": "+exceptionMessage);};
    }
    
}

