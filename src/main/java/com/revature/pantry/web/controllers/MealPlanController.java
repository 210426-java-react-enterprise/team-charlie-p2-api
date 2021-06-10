package com.revature.pantry.web.controllers;



//import com.revature.pantry.models.MealPlan;
import com.revature.pantry.models.MealTime;
import com.revature.pantry.models.User;
import com.revature.pantry.services.MealService;
import com.revature.pantry.services.RecipeService;
import com.revature.pantry.services.UserService;
import com.revature.pantry.web.dtos.MealPlanDTO;
import com.revature.pantry.web.dtos.MealTimeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
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
        
//        @PostMapping(value = "/save/plan", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
//        public MealPlan savePlan(@RequestBody @Valid MealPlanDTO mealPlan) {
//
//                MealPlan mealPlanToSave = new MealPlan();
//                List<MealTime> mealTimeList= new ArrayList<MealTime>();
//
//                User user = userService.findUserById(mealPlan.getUserId());
//                mealPlanToSave.setUser(user);
//
//                mealPlan.getDayPlanList().stream().forEach(mealTimeDTO -> {
//                        Map<String, Integer> supportMap = mealTimeDTO.getDayPlan();
//                        supportMap.forEach((time, recipeId)->{
//                                MealTime mealTime = new MealTime();
//                                mealTime.setDate(mealTimeDTO.getDate());
//                                mealTime.setMealTime(String.valueOf(time));
//                                mealTimeList.add(mealTime);
//                                });
//                        });
//
//                mealPlanToSave.setMealTimes(mealTimeList);
//                return  mealService.save(mealPlanToSave);
//        }
        
        @GetMapping(value = "/find/id", produces = APPLICATION_JSON_VALUE)
//V2
//        public MealPlanDTO findMealPlanByUserId(@RequestParam @Valid int userId){
//                User user = userService.findUserById(userId);
//                MealPlan mealPlan = mealService.findMealPlanByUserId(user);
//                MealPlanDTO mealPlanDTO = new MealPlanDTO();
//                mealPlanDTO.setUserId(user.getId());
//                mealPlan.getMealTimes().forEach(mealTime -> {
//                        MealTimeDTO mealTimeDTO = new MealTimeDTO();
//                        mealTimeDTO.setDate(mealTime.getDate());
//                        //mealTimeDTO.addToDayPlan(mealTime.getMealTime(),mealtime.g);
//                        //mealPlanDTO.addToDayPlan(mealTime);
//                });
//
//                return mealPlanDTO;
//        }

        //V3
                public MealPlanDTO findMealPlanByUserId(@RequestParam @Valid int userId){
                MealPlanDTO mealPlanDTO = new MealPlanDTO();
                User user = userService.findUserById(userId);
                mealPlanDTO.setUserId(user.getId());
                mealPlanDTO.setDayPlanList(user.getMealTimesList());
                return mealPlanDTO;
        }
        
}
