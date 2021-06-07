package com.revature.pantry.services;

import com.revature.pantry.exceptions.InvalidRequestException;
import com.revature.pantry.models.Recipe;
import com.revature.pantry.models.User;
import com.revature.pantry.models.UserFavoriteRecipe;
import com.revature.pantry.repos.FavoriteRecipeRepository;
import com.revature.pantry.repos.RecipeRepository;
import com.revature.pantry.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private FavoriteRecipeRepository favoriteRecipeRepository;
    private RecipeRepository recipeRepository;

    @Autowired
    public UserService (UserRepository userRepository, RecipeRepository recipeRepository, FavoriteRecipeRepository favoriteRecipeRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.favoriteRecipeRepository = favoriteRecipeRepository;
    }


    public User authenticate (String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    public User registerUser (User user) {
        user.setRole(User.Role.BASIC_USER);
        return userRepository.save(user);
    }

    public UserFavoriteRecipe addFavorite(String username, int recipeID) {
        User user = userRepository.findUserByUsername(username);
        Recipe recipe = recipeRepository.findById(recipeID)
                .orElseThrow(InvalidRequestException::new);
        UserFavoriteRecipe ufr = new UserFavoriteRecipe();

        ufr.setFavorite(true);
        ufr.setUser(user);
        ufr.setRecipe(recipe);
        favoriteRecipeRepository.save(ufr);
        return ufr;
    }

    public Set<Recipe> getFavoriteRecipes(String username) {
        User user = userRepository.findUserByUsername(username);
        Set<UserFavoriteRecipe> favorites = favoriteRecipeRepository.findByUserId(user.getId());
        Set<Recipe> recipes = new HashSet<>();
        favorites.stream()
                .filter(UserFavoriteRecipe::isFavorite)
                .forEach(favorite -> recipes.add(favorite.getRecipe()));

        return recipes;
    }

    public void removeFavorite(String username, int recipeID) {
        User user = userRepository.findUserByUsername(username);
        Set<UserFavoriteRecipe> favorites = favoriteRecipeRepository.findByUserId(user.getId());
        UserFavoriteRecipe noLongerFavorite = favorites.stream().filter(favorite -> favorite.getRecipe().getId() == recipeID)
                .findFirst()
                .orElseThrow(InvalidRequestException::new);
        noLongerFavorite.setFavorite(false);
        favoriteRecipeRepository.save(noLongerFavorite);
    }

}
