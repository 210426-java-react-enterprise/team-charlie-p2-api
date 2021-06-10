package com.revature.pantry.repos;

import com.revature.pantry.models.MealTime;
import com.revature.pantry.models.User;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealTimeRepository extends JpaRepository<MealTime, Integer> {
    
    List<MealTime> findMealTimesByDate(LocalDate date);
    
}
