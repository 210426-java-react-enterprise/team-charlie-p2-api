package com.revature.pantry.web.controllers;

import com.revature.pantry.models.Recipe;
import com.revature.pantry.models.User;
import com.revature.pantry.repos.UserRepository;
import com.revature.pantry.services.UserService;
import com.revature.pantry.util.JwtUtil;
import com.revature.pantry.web.dtos.Credentials;
import com.revature.pantry.web.dtos.Principal;
import com.revature.pantry.web.dtos.RecipeDTO;
import com.revature.pantry.web.dtos.NewUserDTO;
import com.revature.pantry.web.dtos.UserDTO;
import com.revature.pantry.web.security.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public void registerUser (@RequestBody NewUserDTO user) {
        userService.registerUser(user);
    }

    @GetMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    @Secured(allowedRoles = {"ADMIN", "BASIC_USER"})
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping(value = "/account", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestBody @Valid Credentials creds, HttpServletRequest req) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        userService.removeUser(username, creds);
    }

    @PostMapping(value = "/favorite", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO favoriteRecipe(@RequestBody @Valid RecipeDTO recipeDTO, HttpServletRequest req) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        return userService.addFavorite(recipeDTO, username);
    }

    @PostMapping(value = "/favorites", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO favoriteRecipes(@RequestBody @Valid List<RecipeDTO> recipeDTO, HttpServletRequest req) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        return userService.addFavorites(recipeDTO, username);
    }

    @DeleteMapping(value = "/favorite/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeFavorite(HttpServletRequest req, @PathVariable int recipeId) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        userService.removeFavoriteRecipe(username, recipeId);
    }

    @GetMapping(value = "/favorite")
    @ResponseStatus(HttpStatus.OK)
    public Set<Recipe> getFavoriteRecipes(HttpServletRequest req) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        return userService.getFavoriteRecipes(username);
    }

}
