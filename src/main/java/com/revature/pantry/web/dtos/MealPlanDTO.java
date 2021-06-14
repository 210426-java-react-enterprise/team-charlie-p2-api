package com.revature.pantry.web.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MealPlanDTO {

    @NotNull
    @JsonProperty("user-id")
    private int userId;
    
    //V3
    @NotNull
    @JsonProperty("day-plan-list")
    private List<MealTimeDTO> dayPlanList;
    
    
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
