package com.revature.pantry.web.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.pantry.models.MealTime;
import io.swagger.models.auth.In;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MealPlanDTO {

    @NotNull
    @JsonProperty("user-id")
    private int userId;
    
    //V3
    @NotNull
    @JsonProperty("day-plan-list")
    private List<@Valid MealTimeDTO> dayPlanList;
    
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
    //V3
    public List<MealTimeDTO> getDayPlanList() {
        return dayPlanList;
    }
    
    public void setDayPlanList(List<MealTimeDTO> dayPlanList) {
        this.dayPlanList = dayPlanList;
    }
    
    public void addDayPlan(MealTimeDTO mealTimeDTO){
        dayPlanList.add(mealTimeDTO);
    }
    
    public void removeDayPlan(MealTimeDTO mealTimeDTO){
        dayPlanList.remove(mealTimeDTO);
    }
}
