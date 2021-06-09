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
    private int id;
    
    //Moved from MealPlan to here
    @NotNull
    @Column(name = "meal_date")
    @JsonProperty("date")
    LocalDate date;
    
    @NotNull
    @Column
    @JsonProperty("time")
    private String mealTime;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="meal_plan_id")
    private int mealPlanId;
    
    @ManyToMany (cascade = {CascadeType.ALL})
    @JoinTable(
            name = "recipe_meal_times",
            joinColumns = {@JoinColumn(name = "meal_time_id")},
            inverseJoinColumns = {@JoinColumn(name="recipe_id")}
    )
    private List<Recipe> recipes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
