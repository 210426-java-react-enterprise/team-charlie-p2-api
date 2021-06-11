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

    public User authenticate(String username, String password) {
        User user = userRepository.findUserByUsernameAndPassword(username, password);
        if (user == null) {
            throw new AuthenticationException("You inputted an invalid username or password!");
        }
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    public User findUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(InvalidRequestException::new);
    }

    public User registerUser(Registration newUser) {
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

    public boolean removeUser(String username, Credentials creds) {

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

    public UserDTO addFavorite(RecipeDTO recipeDTO, String username) {
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
            return userDTO;
        } else {
            throw new InvalidRequestException("A wrong username got here somehow.");
        }
    }

    public UserDTO addFavorites(List<RecipeDTO> recipeDTO, String username) {
        UserDTO savedFavoriteUser = null;
        for (RecipeDTO recipe : recipeDTO) {
            System.out.println(recipe);
            savedFavoriteUser = addFavorite(recipe, username);
        }

        return savedFavoriteUser;
    }

    public boolean removeFavoriteRecipe(String username, int recipeId) {
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

    public Set<Recipe> getFavoriteRecipes(String username) {
        Optional<User> _user = Optional.ofNullable(userRepository.findUserByUsername(username));

        if (_user.isPresent()) {
            return _user.get().getFavorites();
        } else {
            throw new InvalidRequestException();
        }
    }

    public boolean saveMealPlan(User user) {
        userRepository.save(user);
        return true;
    }
}


