package com.revature.pantry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meal_times")
public class MealTime {

    @Id
    @Column(name = "meal_time_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mealTimeId;
    
    //Moved from MealPlan to here
    @NotNull
    @Column(name = "meal_date")
    @JsonProperty("date")
    LocalDate date;
    
    @NotNull
    @Column
    @JsonProperty("time")
    private String mealTime;
 
//V2
//    @NotNull
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="meal_plan_id")
//    private MealPlan mealPlan;
    
    //V3
    @ManyToMany(mappedBy = "mealTimeList")
    @JsonIgnore
    List<User> userList = new ArrayList<>();
    
    @ManyToMany (cascade = {CascadeType.ALL})
    @JoinTable(
            name = "recipe_meal_times",
            joinColumns = {@JoinColumn(name = "meal_time_id")},
            inverseJoinColumns = {@JoinColumn(name="recipe_id")}
    )
    private List<Recipe> recipes;
    
    public int getMealTimeId() { return mealTimeId; }
    
    public void setMealTimeId(int mealTimeId) { this.mealTimeId = mealTimeId; }
    
    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public List<User> getUserList() {
        return userList;
    }
    
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    
    public void addUser(User user){
        this.userList.add(user);
        user.getMealTimesList().add(this);
    }
    
    public void removeUser(User user){
        this.userList.remove(user);
        user.getMealTimesList().remove(this);
    }
    
    public List<Recipe> getRecipes() {
        return recipes;
    }
    
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
    
    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
        recipe.getMealTimes().add(this);
    }
    
    public void removeRecipe(Recipe recipe){
        this.recipes.remove(recipe);
        recipe.getMealTimes().remove(this);
    }
}
