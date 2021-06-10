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
        
        @PutMapping(value = "/save/plan", consumes = APPLICATION_JSON_VALUE)
//        public void savePlan(@RequestBody @Valid MealPlanDTO mealPlan) {
//                List<MealTime> mealTimeList = new ArrayList<>();
//                User user = new User();
//                mealPlan.getDayPlanList().stream().forEach(mealTimeDTO -> {
//                        MealTime mealTime = new MealTime();
//                        mealTime.setDate(mealTimeDTO.getDate());
//                        mealTime.setMealTime(mealTimeDTO.getTime());
//                        mealTime.setRecipe(mealTimeDTO.getRecipe());
//                        mealTimeList.add(mealTime);
//                });
//                user.setId(mealPlan.getUserId());
//                user.setMealTimesList(mealTimeList);
//                userService.saveMealPlan(user);
//        }
        public void savePlan(@RequestBody @Valid MealTime mealPlan) {
        
        }
        
        

        //V3
        @GetMapping(value = "/find", produces = APPLICATION_JSON_VALUE)
        public MealPlanDTO findMealPlanByUserId(@RequestParam @Valid int userId){
        MealPlanDTO mealPlanDTO = new MealPlanDTO();
        User user = userService.findUserById(userId);
        mealPlanDTO.setUserId(user.getId());
        List<MealTimeDTO> mealTimeList = new ArrayList<>();
        user.getMealTimesList().stream().forEach(mealTime -> {
                MealTimeDTO mealTimeDTO = new MealTimeDTO();
                mealTimeDTO.setDate(mealTime.getDate());
                mealTimeDTO.setTime(mealTime.getMealTime());
                mealTimeDTO.setRecipe(mealTime.getRecipe());
                mealTimeList.add(mealTimeDTO);
        });
        //mealPlanDTO.setDayPlanList(user.getMealTimesList());
        mealPlanDTO.setDayPlanList(mealTimeList);
        return mealPlanDTO;
        }
        
}
