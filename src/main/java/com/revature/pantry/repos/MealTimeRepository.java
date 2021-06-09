package com.revature.pantry.repos;

import com.revature.pantry.models.MealTime;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealTimeRepository extends JpaRepository <MealTime, Integer> {
    
    MealTime findMealTimeById(int mealTimeId);
    
}
