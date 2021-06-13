package com.revature.pantry.models;

import com.fasterxml.jackson.annotation.*;
import com.revature.pantry.web.dtos.RecipeDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "recipes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe{


    @Id
    @Column(name = "recipe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(nullable = false)
    @JsonProperty("label")
    private String label;

    @NotNull
    @Column(nullable = false)
    @JsonProperty("calories")
    private int calories;

    @NotNull
    @Column(nullable = false)
    @JsonProperty("yield")
    private int yield;

    @NotNull
    @Column(nullable = false)
    @JsonProperty("url")
    private String url;

    @JsonProperty("image")
    private String image;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.recipe", cascade = CascadeType.ALL)
    private Set<FavoriteRecipe> favoriteRecipes = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "recipe")
    private List<MealTime> mealTimeList;
    
    @JsonIgnore
    @Column(name = "ingredient_lines")
    private String[] ingredientLines;

    public Recipe() {

    }

    public Recipe(String label, String url, String image) {
        this.label = label;
        this.url = url;
        this.image = image;
    }

    public Recipe(String label, String url, String image, int calories, int yield) {
        this(label, url, image);
        this.calories = calories;
        this.yield = yield;
    }

    public Recipe(RecipeDTO recipeDTO) {
        this.yield = recipeDTO.getYield();
        this.calories = recipeDTO.getCalories();
        this.url = recipeDTO.getUrl();
        this.label = recipeDTO.getLabel();
        this.image = recipeDTO.getImage();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<MealTime> getMealTimeList() {
        return mealTimeList;
    }
    
    public void setMealTimeList(List<MealTime> mealTimeList) {
        this.mealTimeList = mealTimeList;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getYield() {
        return yield;
    }

    public void setYield(int yield) {
        this.yield = yield;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(String[] ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public Set<FavoriteRecipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(Set<FavoriteRecipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }
}
