package com.revature.pantry.models;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class UserRecipeId implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Recipe recipe;

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }


    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
