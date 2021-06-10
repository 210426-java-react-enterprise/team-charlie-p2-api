package com.revature.pantry.web.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MealTimeDTO {
    
    @NotNull
    @DateTimeFormat
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    private LocalDate date;
    
    @NotNull
    @JsonProperty("day-plan")
    private Map<String, Integer> dayPlan;
    
    public MealTimeDTO() { super();}
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public Map<String, Integer> getDayPlan() {
        return dayPlan;
    }
    
    public void setDayPlan(Map<String, Integer> dayPlan) {
        this.dayPlan = dayPlan;
    }
    
    public void addToDayPlan(String time, int recipeId){
        dayPlan.put(time, recipeId);
    }
    
    @Override
    public String toString() {
        return "MealTimeDTO{" +
                "date=" + date +
                ", dayPlan=" + dayPlan +
                '}';
    }
}
