package com.revature.pantry.web.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MealPlanDTO {

    @NotNull
    @JsonProperty("user-id")
    private int userId;
    
    @NotNull
    @JsonProperty("day-plan-list")
    private List<MealTimeDTO> dayPlanList;
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public List<MealTimeDTO> getDayPlanList() {
        return dayPlanList;
    }
    
    public void setDayPlanList(List<MealTimeDTO> dayPlanList) {
        this.dayPlanList = dayPlanList;
    }
}
