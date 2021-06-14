package com.revature.pantry.repos;

import com.revature.pantry.models.MealTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealTimeRepository extends JpaRepository<MealTime, Integer> {
    
    List<MealTime> findMealTimesByDate(LocalDate date);
    
}
