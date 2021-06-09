package com.revature.pantry.web.controllers;



import com.revature.pantry.models.MealPlan;
import com.revature.pantry.models.MealTime;
import com.revature.pantry.models.User;
import com.revature.pantry.services.MealService;
import com.revature.pantry.services.RecipeService;
import com.revature.pantry.services.UserService;
import com.revature.pantry.web.dtos.MealPlanDTO;
import com.revature.pantry.web.dtos.MealTimeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/meals")
public class MealPlanController {
        
        private MealService mealService;
        private RecipeService recipeService;
        private UserService userService;
        
        @Autowired
        public MealPlanController(MealService mealservice, RecipeService recipeService, UserService userService) {
                this.mealService = mealservice;
                this.recipeService = recipeService;
                this.userService = userService;
        }
        
        @GetMapping(value = "/save/plan", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
        public MealPlan savePlan(@RequestBody @Valid MealPlanDTO mealPlan) {
                
                MealPlan mealPlanToSave = new MealPlan();
                List<MealTime> mealTimeList= new ArrayList<MealTime>();
                
                User user = userService.findUserById(mealPlan.getUserId());
                mealPlanToSave.setUser(user);
                
                mealPlan.getDayPlanList().stream().forEach(mealTimeDTO -> {

                        Map<String, Integer> supportMap = mealTimeDTO.getDayPlan();
                        supportMap.forEach((time, recipeId)->{
                                MealTime mealTime = new MealTime();
                                mealTime.setDate(mealTimeDTO.getDate());
                                mealTime.setMealTime(String.valueOf(time));
                                mealTimeList.add(mealTime);
                                });
                        });
                
                mealPlanToSave.setMealTimes(mealTimeList);
                return  mealService.save(mealPlanToSave);
        }
}
