package com.revature.pantry.web.controllers;

import com.revature.pantry.models.User;
import com.revature.pantry.repos.UserRepository;
import com.revature.pantry.services.UserService;
import com.revature.pantry.web.security.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController (UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public User registerUser (@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    @Secured(allowedRoles = {"ADMIN"})
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
