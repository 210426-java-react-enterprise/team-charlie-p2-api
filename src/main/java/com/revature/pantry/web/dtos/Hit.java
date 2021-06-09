package com.revature.pantry.web.dtos;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hit {

    @JsonProperty("recipe")
    private RecipeDTO recipe;

    @JsonProperty("bookmarked")
    private boolean bookmarked;

    @JsonProperty("bought")
    private boolean bought;

    public RecipeDTO getRecipe() {
        return recipe;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public boolean isBought() {
        return bought;
    }
}
