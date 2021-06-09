package com.revature.pantry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    public User() {

    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<MealPlan> mealPlans;

    @Enumerated(value = EnumType.STRING)
    Role role;

//    @JsonIgnore
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<UserFavoriteRecipe> favorites;

    @ManyToMany (cascade = {CascadeType.ALL})
    @JoinTable (
            name = "user_favorite_recipe",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipe_id")}
    )
    private Set<Recipe> favorites = new HashSet<>();

    public Set<Recipe> getFavorites() {
        return favorites;
    }

    public void removeFavorite (Recipe recipe) {
        this.favorites.remove(recipe);
        recipe.getUsers().remove(this);
    }

    public void addFavorite(Recipe recipe) {
        this.favorites.add(recipe);
        recipe.getUsers().add(this);
    }

    public void setFavorites(Set<Recipe> favorites) {
        this.favorites = favorites;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<MealPlan> getMealPlans() {
        return mealPlans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public enum Role {
        BASIC_USER, ADMIN;
    }
}
