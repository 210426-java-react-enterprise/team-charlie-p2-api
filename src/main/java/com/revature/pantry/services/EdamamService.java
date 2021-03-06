package com.revature.pantry.services;

import com.revature.pantry.exceptions.*;
import com.revature.pantry.web.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import java.util.*;
import java.util.regex.*;

@Service
public class EdamamService {

    @Value("${api.app_id}")
    private String app_id;

    @Value("${api.app_key}")
    private String app_key;

    private final RestTemplate restTemplate;


    @Autowired
    public EdamamService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /**
     * Takes in a query string and passes that to Edamam to search for recipes.
     *
     * @param q the query in the form of a string
     * @return the first 10 recipes Edamam finds
     * @author Kevin Chang
     */
    public List<RecipeDTO> getRecipesFromEdamam(@RequestParam String q){

        String apiUrl = String.format("https://api.edamam.com/search?q=%s&app_id=%s&app_key=%s", q, app_id, app_key);
        ResponseEntity responseEntity = restTemplate.getForEntity(apiUrl, Response.class);
        Response response = (Response) responseEntity.getBody();

        List<Hit> hits = response.getHits();
        List<RecipeDTO> recipes = new ArrayList<RecipeDTO>();
        hits.forEach((hit -> recipes.add(hit.getRecipe())));

        return recipes;
    }

    public boolean isIngredientValid(String q){
        boolean check = true;
        //splits q by plus signs
        List<String> ingredients = Arrays.asList(q.split(" "));

        //String regex = "^([a-zA-Z+]+){3,40}$|^[0]$";
        //accounts for in case the user puts in a space between words
        String regex = "^([a-zA-Z]+[ ]?[a-zA-Z]?){3,40}$|^[0]$";
        StringBuilder invalidIngredients = new StringBuilder();

        for (String ingredient: ingredients) {
            if(Pattern.matches(regex, ingredient)){

            }
            else{
                invalidIngredients.append(ingredient).append(", ");
                check = false;
            }
        }


        if(!check){
            throw new InvalidIngredientException("Ingredient(s): " + invalidIngredients + "are not valid!");
        }
        return check;
    }

}