package com.revature.pantry.web.dtos;

import com.revature.pantry.models.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class UserDTO {

    public UserDTO() {

    }

    @NotNull
    private int user_id;

    @NotNull
    private String username;

    private Set<Recipe> favorites;

    private List<MealTime> mealTimeList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Recipe> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Recipe> favorites) {
        this.favorites = favorites;
    }

    public List<MealTime> getMealTimeList() {
        return mealTimeList;
    }

    public void setMealTimeList(List<MealTime> mealTimeList) {
        this.mealTimeList = mealTimeList;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
