package com.revature.pantry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    public User() {

    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Email
    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    MealPlan mealPlans;

    @Enumerated(value = EnumType.STRING)
    Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserFavoriteRecipe> favorites;

    public List<UserFavoriteRecipe> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<UserFavoriteRecipe> favorites) {
        this.favorites = favorites;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public MealPlan getMealPlans() {
        return mealPlans;
    }
    
    public void setMealPlans(MealPlan mealPlans) { this.mealPlans = mealPlans; }
    
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
