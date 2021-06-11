package com.revature.pantry.web.dtos;

import com.revature.pantry.models.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class UserDTO {

    public UserDTO() {

    }

<<<<<<< HEAD
    public UserDTO(String username, Set<Recipe> favorites) {
        this.username = username;
        this.favorites = favorites;
    }

    public UserDTO(String username, Set<Recipe> favorites, int userId) {
        this(username, favorites);
        this.userId = userId;
    }
=======
    public UserDTO(@NotNull String username, Set<Recipe> favorites, List<MealTime> mealTimeList) {
        this.username = username;
        this.favorites = favorites;
        this.mealTimeList = mealTimeList;
    }

    @NotNull
    private int user_id;
>>>>>>> 4661a3d9f47723bc92abb965348890b52a86dbe2

    @NotNull
    private String username;

    private int userId;

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
