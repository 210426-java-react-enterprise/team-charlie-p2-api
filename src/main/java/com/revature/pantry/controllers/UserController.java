package com.revature.pantry.controllers;

import com.revature.pantry.models.User;
import com.revature.pantry.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public User registerUser (@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
