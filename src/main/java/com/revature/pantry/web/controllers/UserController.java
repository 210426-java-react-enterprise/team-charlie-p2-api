package com.revature.pantry.web.controllers;

import com.revature.pantry.models.Recipe;
import com.revature.pantry.models.User;
import com.revature.pantry.models.UserFavoriteRecipe;
import com.revature.pantry.repos.UserRepository;
import com.revature.pantry.services.UserService;
import com.revature.pantry.util.JwtUtil;
import com.revature.pantry.web.dtos.Principal;
import com.revature.pantry.web.security.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${jwt.header}")
    private String header;

    private UserRepository userRepository;
    private UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController (UserRepository userRepository, UserService userService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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

    @PostMapping(value = "/favorite", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
    public UserFavoriteRecipe addFavorite(@RequestParam int id, HttpServletRequest req) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        return userService.addFavorite(username, id);
    }

    @GetMapping(value = "/favorite", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
    public Set<Recipe> getFavorite(HttpServletRequest req) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        return userService.getFavoriteRecipes(username);
    }

    @PatchMapping(value = "/favorite", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
    public void removeFavorite(@RequestParam int id, HttpServletRequest req) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        userService.removeFavorite(username, id);
    }


}
