package com.revature.pantry.web.controllers;

import com.revature.pantry.models.Recipe;
import com.revature.pantry.models.User;
import com.revature.pantry.repos.UserRepository;
import com.revature.pantry.services.UserService;
import com.revature.pantry.util.JwtUtil;
import com.revature.pantry.web.dtos.*;
import com.revature.pantry.web.security.Secured;
import jdk.jfr.internal.LogLevel;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.*;

/**
 * UserController
 *
 * The main controller for handling requests to do with users.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${jwt.header}")
    private String header;

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;


    @Autowired
    public UserController (UserRepository userRepository, UserService userService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Takes in a registration JSON from the request, validates it, then calls the service to register a user.
     *
     * @param user the JSON for registration
     * @author Richard Taylor
     */
    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO registerUser (@RequestBody @Valid Registration user, HttpServletResponse resp) {
        User newUser = userService.registerUser(user);
        return new UserDTO(newUser);
    }

    /**
     * Returns a list of all registered users. Can only be accessed by an admin.
     * @return a list of all users.
     */
    @GetMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    @Secured(allowedRoles = {"ADMIN"})
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Takes in a set of credentials and performs basic validation on them. Then calls the services to delete a user account.
     *
     * @param creds the credentials as a JSON
     * @param req the request provided by Spring
     * @author Austin Knauer
     */
    @DeleteMapping(value = "/account", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
    public void deleteUser(@RequestBody @Valid Credentials creds, HttpServletRequest req) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        userService.removeUser(username, creds);
    }

    /**
     * Takes in a JSON for a recipe and calls the service to add it to the list of the user's favorites.
     *
     * @param recipeDTO the JSON of the recipe
     * @param req the request provided by Spring
     * @return an updated DTO of the user
     * @author Richard Taylor
     */
    @PostMapping(value = "/favorite", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
    public UserDTO favoriteRecipe(@RequestBody @Valid RecipeDTO recipeDTO, HttpServletRequest req) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        return userService.addFavorite(recipeDTO, username);
    }

    /**
     * Takes a JSON for a list of recipes and calls the service to add it to the list of the user's favorites
     *
     * @param recipeDTO the JSON of the recipes
     * @param req the request provided by Spring
     * @return an updated DTO of the user
     * @author Kevin Chang
     */
    @PostMapping(value = "/favorites", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
    public UserDTO favoriteRecipes(@RequestBody @Valid List<RecipeDTO> recipeDTO, HttpServletRequest req) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        return userService.addFavorites(recipeDTO, username);
    }

    @PatchMapping(value = "/favorite")
    @ResponseStatus(HttpStatus.OK)
    @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
    public void updateTimesPrepared(@RequestBody FavoriteDTO favoriteDTO, HttpServletRequest req) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        userService.updateTimesPrepared(favoriteDTO, username);
    }

    /**
     * Removes the recipe from the user's favorite list that corresponds to the recipeId of the path variable
     * @param req the request provided by Spring
     * @param recipeId the Id of the recipe to be removed
     * @author Richard Taylor
     */
    @DeleteMapping(value = "/favorite/{recipeId}")
    @ResponseStatus(HttpStatus.OK)
   // @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
    public void removeFavorite(HttpServletRequest req, @PathVariable int recipeId) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        userService.removeFavoriteRecipe(username, recipeId);
    }

    /**
     * calls the service and returns the list of the user's favorite recipes
     *
     * @param req the request provided by Spring
     * @return a list of recipes in the form of a JSON
     * @author Richard Taylor
     */
    @GetMapping(value = "/favorite")
    @ResponseStatus(HttpStatus.OK)
   // @Secured(allowedRoles = {"BASIC_USER", "ADMIN"})
    public List<FavoriteDTO> getFavoriteRecipes(HttpServletRequest req) {
        String username = jwtUtil.getUsernameFromToken(req.getHeader(header));
        return userService.getFavoriteRecipes(username);
    }

}
