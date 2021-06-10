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

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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

//V2
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "meal_plan_id")
//    private MealPlan mealPlan;
    
    //V3
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name= "user_meal_times",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name ="meal_time_id")}
    )
    private List<MealTime> mealTimeList;
    
    
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

//V2
//    public MealPlan getMealPlan() {
//        return mealPlan;
//    }
//
//    public void setMealPlan(MealPlan mealPlan) { this.mealPlan = mealPlan; }
    
    public List<MealTime> getMealTimesList() {
        return mealTimeList;
    }
    
    public void setMealTimesList(List<MealTime> mealTimeList) {
        this.mealTimeList = mealTimeList;
    }
    
    public void addMealTimeToList(MealTime mealTime){
        this.mealTimeList.add(mealTime);
        mealTime.getUserList().add(this);
        
    }
    
    public void removeMealTimeToList(MealTime mealTime){
        this.mealTimeList.remove(mealTime);
        mealTime.getUserList().remove(this);
    }
    
    public int getId() { return id;
    }

    public void setId(int id) { this.id = id; }

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
