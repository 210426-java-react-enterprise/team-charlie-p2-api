package com.revature.pantry.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "meal_plans")
public class MealPlan {

    @Id
    @Column(name = "meal_plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private int userId;
    
   @OneToMany(mappedBy = "meal-plan")
    List<MealTime> mealTimes;
   
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public List<MealTime> getMealTimes() {
        return mealTimes;
    }
    
    public void setMealTimes(List<MealTime> mealTimes) {
        this.mealTimes = mealTimes;
    }
    
}
