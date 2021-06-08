package com.revature.pantry.web.controllers;



import com.revature.pantry.models.MealTime;
import com.revature.pantry.services.MealService;
import com.revature.pantry.web.dtos.MealTimeDto;
import com.revature.pantry.web.dtos.RecipeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/meals")
public class MealPlanController {

        private MealService mealServicel

        @Autowired
        public MealPlanController(MealService mealservice){this.mealServicel = mealservice;}

        @GetMapping(value = "/save/plan", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE);
        public MealTime savePlan(@RequestBody List<MealTimeDto> dayPlan){
                for(MealTimeDto mealTime : dayPlan){
                        if(mealServicel.isMealTimeValid(mealTime)){
                        
                        }
                }
                
                return mealServicel.savePlan
         }
        
    
}
