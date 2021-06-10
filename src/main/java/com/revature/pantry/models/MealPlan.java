package com.revature.pantry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
//V2
//@Entity
//@Table(name = "meal_plans")
public class MealPlan {

//V2
//
//    @Id
//    @Column(name = "meal_plan_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mealPlanId;

//V2
//    @OneToOne(mappedBy = "mealPlan")
//    @JoinColumn(name="user_id", referencedColumnName = "id")
//    @JsonIgnore()
    private User user;
    
//V2
//   @OneToMany(mappedBy = "mealPlan")
    List<MealTime> mealTimes;
   
    
    public int getId() {
        return mealPlanId;
    }

    public void setId(int mealPlanId) {
        this.mealPlanId = mealPlanId;
    }
    
    public User getUser() { return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public List<MealTime> getMealTimes() {
        return mealTimes;
    }
    
    public void setMealTimes(List<MealTime> mealTimes) {
        this.mealTimes = mealTimes;
    }
    
}
