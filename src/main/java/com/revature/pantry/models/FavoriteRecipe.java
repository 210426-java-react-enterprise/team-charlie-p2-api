package com.revature.pantry.models;

import javax.persistence.*;

@Entity
@Table(name = "user_favorite_recipes")
@AssociationOverrides({
        @AssociationOverride(name = "pk.recipe",
        joinColumns = @JoinColumn(name = "recipe_id")),
        @AssociationOverride(name = "pk.user",
        joinColumns = @JoinColumn(name = "user_id"))
})
public class FavoriteRecipe {

    public FavoriteRecipe() {

    }

    public FavoriteRecipe(User user, Recipe recipe, boolean favorite, Integer timesPrepared) {
        this.setUser(user);
        this.setRecipe(recipe);
        this.favorite = favorite;
        this.timesPrepared = timesPrepared;
    }

    @EmbeddedId
    private FavoriteRecipeId pk = new FavoriteRecipeId();

    private boolean favorite;

    @Column(name = "times_prepared")
    private Integer timesPrepared;

    public FavoriteRecipeId getPk() {
        return pk;
    }

    public void setPk(FavoriteRecipeId pk) {
        this.pk = pk;
    }

    public Recipe getRecipe() {
        return getPk().getRecipe();
    }

    public void setRecipe(Recipe recipe) {
        this.pk.setRecipe(recipe);
    }

    public User getUser() {
        return getPk().getUser();
    }

    public void setUser(User user) {
        this.pk.setUser(user);
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getTimesPrepared() {
        return timesPrepared;
    }

    public void setTimesPrepared(Integer timesPrepared) {
        this.timesPrepared = timesPrepared;
    }
}
