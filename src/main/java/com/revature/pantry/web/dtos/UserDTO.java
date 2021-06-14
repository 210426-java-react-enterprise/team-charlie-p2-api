package com.revature.pantry.web.dtos;

import com.revature.pantry.models.*;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserDTO {

    @NotNull
    private int user_id;

    @NotNull
    private String username;

    private List<FavoriteDTO> favorites;

    private List<MealTime> mealTimeList;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.user_id = user.getId();
        this.favorites = user.getFavorites();
        this.mealTimeList = user.getMealTimesList();
    }

    public UserDTO(String username, List<FavoriteDTO> favorites) {
        this.username = username;
        this.favorites = favorites;
    }

    public UserDTO(String username, List<FavoriteDTO> favorites, int userId) {
        this(username, favorites);
        this.user_id = userId;
    }
    public UserDTO(@NotNull String username, List<FavoriteDTO> favorites, List<MealTime> mealTimeList) {
        this.username = username;
        this.favorites = favorites;
        this.mealTimeList = mealTimeList;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<FavoriteDTO> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<FavoriteDTO> favorites) {
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
