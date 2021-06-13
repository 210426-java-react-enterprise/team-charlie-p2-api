package com.revature.pantry.web.dtos;

import com.revature.pantry.models.FavoriteRecipe;

public class FavoriteDTO {

    private String label;

    private String url;

    private String image;

    private Integer calories;

    private Integer yield;

    private Integer timesPrepared;

    private Integer recipeId;

    public FavoriteDTO(FavoriteRecipe recipe) {
        this.calories = recipe.getRecipe().getCalories();
        this.yield = recipe.getRecipe().getYield();
        this.image = recipe.getRecipe().getImage();
        this.url = recipe.getRecipe().getUrl();
        this.label = recipe.getRecipe().getLabel();
        this.recipeId = recipe.getRecipe().getId();
        this.timesPrepared = recipe.getTimesPrepared();
    }

    public FavoriteDTO () {

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getYield() {
        return yield;
    }

    public void setYield(Integer yield) {
        this.yield = yield;
    }

    public Integer getTimesPrepared() {
        return timesPrepared;
    }

    public void setTimesPrepared(Integer timesPrepared) {
        this.timesPrepared = timesPrepared;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }
}
