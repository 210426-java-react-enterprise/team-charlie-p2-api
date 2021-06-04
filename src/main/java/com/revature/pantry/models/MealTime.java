package com.revature.pantry.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "meal_times")
public class MealTime {

    @Id
    @Column(name = "meal_time_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String mealTime;

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

}
