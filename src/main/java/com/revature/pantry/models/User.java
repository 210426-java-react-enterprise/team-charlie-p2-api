package com.revature.pantry.models;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    @ManyToMany (cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_favorite_recipes",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name="recipe_id")}
    )
    List<Recipe> favoriteRecipes;

    public List<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public List<MealPlan> getMealPlans() {
        return mealPlans;
    }

    @OneToMany(mappedBy = "user")
    List<MealPlan> mealPlans;

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
}
