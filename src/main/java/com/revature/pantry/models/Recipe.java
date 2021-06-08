package com.revature.pantry.models;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "recipes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {


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

    @JsonIgnore
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<UserFavoriteRecipe> favorites;

    @JsonIgnore
    @ManyToMany(mappedBy = "recipes")
    private List<MealTime> mealTimes;

    @Column(name = "ingredient_lines")
    private String[] ingredientLines;

    public Recipe() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<MealTime> getMealTimes() {
        return mealTimes;
    }

    public void setMealTimes(List<MealTime> mealTimes) {
        this.mealTimes = mealTimes;
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


    public List<UserFavoriteRecipe> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<UserFavoriteRecipe> favorites) {
        this.favorites = favorites;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", calories=" + calories +
                ", yield='" + yield + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
