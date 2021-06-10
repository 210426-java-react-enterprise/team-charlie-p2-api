package com.revature.pantry.web.dtos;

import com.revature.pantry.models.Recipe;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class UserDTO {

    public UserDTO() {

    }

    @NotNull
    private String username;

    private Set<Recipe> favorites;

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
}
