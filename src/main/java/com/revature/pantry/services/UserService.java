package com.revature.pantry.services;


import com.revature.pantry.exceptions.*;
import com.revature.pantry.models.Recipe;
import com.revature.pantry.models.User;
import com.revature.pantry.repos.RecipeRepository;
import com.revature.pantry.repos.UserRepository;
import com.revature.pantry.web.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * UserService
 *
 * Service class that acts as a middleman between the repository and the endpoints.
 * Contains various functions to handle transactions and database calls.
 *
 * @author Richard Taylor
 * @author Kevin Chang
 * @author Oswaldo Castillo
 * @author Austin Knauer
 * @author Uros Vorkapic
 */
@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private RecipeRepository recipeRepository;

    @Autowired
    public UserService(UserRepository userRepository, RecipeRepository recipeRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    /**
     * Takes the username and password provided and queries the database to see if valid credentials were provided.
     *
     * @param username The username passed in
     * @param password The password passed in
     * @return The authenticated user, throws an exception otherwise
     *
     * @author Kevin Chang
     * @throws AuthenticationException when either credential is incorrect.
     */
    public User authenticate(String username, String password) throws AuthenticationException {
        User user = userRepository.findUserByUsernameAndPassword(username, password);
        if (user == null) {
            throw new AuthenticationException("You inputted an invalid username or password!");
        }
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    /**
     * Finds the user in the database from the id provided. Used as a utility method for other methods.
     *
     * @param userId the user Id to be searched
     * @return The user if there is one, an exception otherwise.
     * @author Oswaldo Castillo
     * @throws InvalidRequestException when there is no user to be found
     */
    public User findUserById(int userId) throws InvalidRequestException{
        return userRepository.findById(userId)
                .orElseThrow(InvalidRequestException::new);
    }

    /**
     * Takes in a (bean validated) registration and uses that to create a user account.
     *
     * @param newUser The registration object containing user details
     * @return The newly registered user
     * @throws ResourcePersistenceException When either the username or email already exists within the database.
     * @author Richard Taylor
     * @author Kevin Chang
     */
    public User registerUser(Registration newUser) throws ResourcePersistenceException{
        User user = new User(newUser.getUsername(), newUser.getPassword(), newUser.getEmail());
        user.setRole(User.Role.BASIC_USER);
        User registeredUser = null;
        try {
            registeredUser = userRepository.save(user);
        } catch (Exception e) {
            throw new ResourcePersistenceException("The registered email or username already exists!");
        }
        return registeredUser;
    }

    /**
     * Takes a username and their provided credentials, ensures a match, then removes the user from the database.
     *
     * @param username The username to be passed in
     * @param creds The provided credentials
     * @return True if the operation succeeds.
     * @throws InvalidRequestException when there is invalid credentials provided
     * @author Austin Knauer
     */
    public boolean removeUser(String username, Credentials creds) throws InvalidRequestException{

        if (!username.equals(creds.getUsername())) {
            throw new InvalidRequestException("Submitted username does not match currently logged in user.");
        }

        User user = userRepository.findUserByUsernameAndPassword(creds.getUsername(), creds.getPassword());

        if (user == null) {
            throw new InvalidRequestException("Submitted password is incorrect.");
        } else {
            userRepository.delete(user);
            return true;
        }
    }

    /**
     * Adds a recipe to the user's list of favorites. Checks the database to see if the recipe exists first before
     * saving the recipe to it.
     *
     * @param recipeDTO The recipe to be added
     * @param username The name of the user
     * @return A DTO for the user
     * @throws InvalidRequestException if a wrong username gets here somehow
     * @author Richard Taylor
     */
    public UserDTO addFavorite(RecipeDTO recipeDTO, String username) throws InvalidRequestException{
        Optional<Recipe> _recipe = recipeRepository.findByLabelAndUrlAndImage(recipeDTO.getLabel(), recipeDTO.getUrl(), recipeDTO.getImage());
        Optional<User> _user = Optional.ofNullable(userRepository.findUserByUsername(username));
        Recipe recipe = null;
        UserDTO userDTO = new UserDTO();

        if (!_recipe.isPresent()) {
            recipe = recipeRepository.save(new Recipe(recipeDTO));
        }
        if (_user.isPresent()) {
            User user = _user.get();
            recipe = (recipe == null) ? _recipe.get() : recipe;

            user.addFavorite(recipe);
            recipe.addUser(user);
            userRepository.save(user);
            recipeRepository.save(recipe);
            userDTO.setFavorites(user.getFavorites());
            userDTO.setUsername(user.getUsername());
            userDTO.setMealTimeList(user.getMealTimesList());
            userDTO.setUser_id(user.getId());
            return userDTO;
        } else {
            throw new InvalidRequestException("A wrong username got here somehow.");
        }
    }

    /**
     * Takes a list of recipes and adds them to the user's list of favorites. Calls <code>addFavorite()</code> for each recipe.
     *
     * @param recipeDTO the list of recipes
     * @param username the username of the user to add the recipes to
     * @return a userDTO containing the user's updated info
     * @author Kevin Chang
     */
    public UserDTO addFavorites(List<RecipeDTO> recipeDTO, String username) {
        UserDTO savedFavoriteUser = null;
        for (RecipeDTO recipe : recipeDTO) {
            System.out.println(recipe);
            savedFavoriteUser = addFavorite(recipe, username);
        }

        return savedFavoriteUser;
    }

    /**
     * Removes a recipe from the user's list of favorites.
     *
     * @param username the username of the user
     * @param recipeId the id of the recipe
     * @return True of the operation succeeds
     * @throws InvalidRequestException if wrong data makes its way to this method
     */
    public boolean removeFavoriteRecipe(String username, int recipeId) throws InvalidRequestException {
        Optional<User> _user = Optional.ofNullable(userRepository.findUserByUsername(username));
        Optional<Recipe> _recipe = recipeRepository.findById(recipeId);

        if (_user.isPresent() && _recipe.isPresent()) {
            User user = _user.get();
            Recipe recipe = _recipe.get();

            user.removeFavorite(recipe);
            recipe.removeUser(user);
            userRepository.save(user);
            recipeRepository.save(recipe);
            return true;
        } else {
            throw new InvalidRequestException();
        }
    }

    /**
     * Returns the list of the user's favorite recipes
     *
     * @param username The username of the user
     * @return The list of recipes from that user
     * @throws InvalidRequestException if an invalid username is provided
     * @author Richard Taylor
     */
    public Set<Recipe> getFavoriteRecipes(String username) throws InvalidRequestException {
        Optional<User> _user = Optional.ofNullable(userRepository.findUserByUsername(username));

        if (_user.isPresent()) {
            return _user.get().getFavorites();
        } else {
            throw new InvalidRequestException();
        }
    }

    /**
     * Saves the user object provided to the database. Utility function for meal plans (will be renamed if this method starts being used for something else)
     * @param user The user to be saved
     * @return True if it succeeds, any SQL exception generated by the repository otherwise.
     */
    
    public UserDTO saveMealPlan(User user){
        UserDTO userDTO = new UserDTO();
        User updatedUser = userRepository.save(user);
        userDTO.setFavorites(updatedUser.getFavorites());
        userDTO.setUsername(updatedUser.getUsername());
        userDTO.setMealTimeList(updatedUser.getMealTimesList());
        userDTO.setUser_id(updatedUser.getId());
        return userDTO;
    }
}


