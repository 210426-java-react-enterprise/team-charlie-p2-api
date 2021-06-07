package com.revature.pantry.web.controllers;

import com.revature.pantry.models.Recipe;
import com.revature.pantry.models.User;
import com.revature.pantry.repos.UserRepository;
import com.revature.pantry.services.UserService;
import com.revature.pantry.web.dtos.Principal;
import com.revature.pantry.web.security.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser (@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    @Secured(allowedRoles = {"ADMIN"})
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(value = "/favorite", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
    public void addFavorite(@RequestBody Recipe recipe, HttpServletRequest req) {
        Principal principal = (Principal) req.getAttribute("principal");
        userService.addFavorite(principal.getId(), recipe);
    }

    @GetMapping(value = "/favorite", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
    public List<Recipe> getFavorite(HttpServletRequest req) {
        Principal principal = (Principal) req.getAttribute("principal");
        return userService.getFavoriteRecipes(principal.getId());
    }

    @DeleteMapping(value = "/favorite", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
    public void removeFavorite(HttpServletRequest req) {
        Principal principal = (Principal) req.getAttribute("principal");
        userService.removeFavorite(principal.getId());
    }


}
