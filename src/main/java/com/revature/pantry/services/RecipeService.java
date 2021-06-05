package com.revature.pantry.services;

import com.revature.pantry.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RecipeService {
    
//    private RecipeRepository recipeRepository;
//
//    @Autowired
//    public RecipeService(RecipeRepository recipeRepository){this.recipeRepository = recipeRepository;}
    
    /**
     * This method is responsible for validate the recipe data inputs against the app constraints
     * @param recipe - Recipe data to be audit
     * @return TRUE if data passed all the constraints / FALSE if not passed the constrains
     */
    
    public boolean isRecipeValid(Recipe recipe){
    
    
    //Evaluates if the string passed is null or empty
    Predicate<String> isNullOrEmpty = str -> (str == null || str.trim().isEmpty());
    
    //Evaluates if the string passed satisfied the pattern passed
    BiPredicate<String, String> isPatternSatisfied = (str, pattern) -> {
        Pattern patternToCompile = Pattern.compile(pattern);
        Matcher patternValidation = patternToCompile.matcher(str);
        return patternValidation.matches();
    };
        
        if(recipe == null) return false;
        //calories > Not null and contains numeric characters
        if(isNullOrEmpty.test(String.valueOf(recipe.getCalories())) || !isPatternSatisfied.test(String.valueOf(recipe.getCalories()) ,"^[0-9]")) return false;
        
        //yield > Not null and contains numeric characters
        if(isNullOrEmpty.test(String.valueOf(recipe.getYield())) || !isPatternSatisfied.test(String.valueOf(recipe.getYield()) ,"^[0-9]")) return false;
        
        //url > Not null and its a valid url address (Address must contains <http://]> or <https://>)
        if(isNullOrEmpty.test(recipe.getUrl()) || !isPatternSatisfied.test(recipe.getUrl(), "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")) return false;
        
        //Return this if passed all the constraints
        return true;
        
    }

    
}


