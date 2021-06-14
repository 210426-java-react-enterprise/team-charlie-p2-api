package com.revature.pantry.web.dtos;


import com.revature.pantry.models.User;
import io.jsonwebtoken.Claims;

public class Principal {

    private int id;
    private String username;
    private User.Role role;

    public Principal(Claims jwtClaims) {
        this.id = Integer.parseInt(jwtClaims.getId());
        this.username = jwtClaims.getSubject();
        this.role = User.Role.valueOf(jwtClaims.get("role", String.class));
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

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}
