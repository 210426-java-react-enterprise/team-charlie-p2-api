package com.revature.pantry.services;

import com.revature.pantry.models.*;
import com.revature.pantry.web.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    public List<RecipeDTO> getRecipesFromEdamam(@RequestParam String q){

        String apiUrl = String.format("https://api.edamam.com/search?q=%s&app_id=%s&app_key=%s", q, app_id, app_key);
        ResponseEntity<Response> edamamResponse = restTemplate.getForEntity(apiUrl, Response.class);
        System.out.println(edamamResponse.getStatusCode());

        Response response = Objects.requireNonNull(edamamResponse.getBody());

        List<Hit> hits = response.getHits();
        List<RecipeDTO> recipes = new ArrayList<RecipeDTO>();
        hits.forEach((hit -> recipes.add(hit.getRecipe())));

        return recipes;
    }

}