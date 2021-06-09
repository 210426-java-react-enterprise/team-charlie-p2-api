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
    private int mealPlanId;
    
    @OneToOne(mappedBy = "mealPlan")
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;
    
   @OneToMany(mappedBy = "mealPlan")
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
