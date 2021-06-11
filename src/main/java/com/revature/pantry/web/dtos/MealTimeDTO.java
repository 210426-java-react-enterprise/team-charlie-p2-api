package com.revature.pantry.web.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.revature.pantry.models.Recipe;
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
    @JsonProperty("time")
    @JsonFormat(shape =  JsonFormat.Shape.STRING)
    private String time;
    
    @NotNull
    @JsonProperty("recipe")
    @JsonFormat(shape =  JsonFormat.Shape.OBJECT)
    private Recipe recipe;

    
    public MealTimeDTO() { super();}
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    //V3
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    
    public Recipe getRecipe() {
        return recipe;
    }
    
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    
    //V3
    @Override
    public String toString() {
        return "MealTimeDTO{" +
                "date=" + date +
                ", time='" + time + '\'' +
                ", recipe=" + recipe +
                '}';
    }
}
