package com.revature.pantry.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
    
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="meal_plan_id")
    private MealPlan mealPlan;
    
    @ManyToMany (cascade = {CascadeType.ALL})
    @JoinTable(
            name = "recipe_meal_times",
            joinColumns = {@JoinColumn(name = "meal_time_id")},
            inverseJoinColumns = {@JoinColumn(name="recipe_id")}
    )
    private List<Recipe> recipes;

    public int getId() {
        return mealTimeId;
    }

    public void setId(int mealTimeId) {
        this.mealTimeId = mealTimeId;
    }

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
}
