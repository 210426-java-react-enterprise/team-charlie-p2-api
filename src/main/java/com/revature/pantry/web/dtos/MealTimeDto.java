package com.revature.pantry.web.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.util.Pair;


import java.time.LocalDate;
import java.util.*;

public class MealTimeDto {
    
    @JsonProperty("date")
    private LocalDate date;
    
    @JsonProperty("recipe")
    private RecipeDto recipe;
    
}
