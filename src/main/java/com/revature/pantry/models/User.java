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
