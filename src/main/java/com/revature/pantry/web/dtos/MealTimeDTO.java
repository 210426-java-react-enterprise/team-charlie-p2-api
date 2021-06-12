package com.revature.pantry.web.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.revature.pantry.models.Recipe;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;


import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MealTimeDTO {
    
    @NotNull
    @DateTimeFormat
    @FutureOrPresent
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    private LocalDate date;
    
    @NotNull
    @Length(min = 0, max = 20)
    @Pattern(regexp = "^[A-Za-z0-9\\s]*$")
    @JsonProperty("time")
    @JsonFormat(shape =  JsonFormat.Shape.STRING)
    private String time;
    
    @NotNull
    @Valid
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
