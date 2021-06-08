package com.revature.pantry.services;


import com.revature.pantry.exceptions.InvalidRequestException;
import com.revature.pantry.models.Recipe;
import com.revature.pantry.exceptions.UserDataIsInvalidException;
import com.revature.pantry.models.User;
import com.revature.pantry.models.UserFavoriteRecipe;
import com.revature.pantry.repos.FavoriteRecipeRepository;
import com.revature.pantry.repos.RecipeRepository;
import com.revature.pantry.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.function.BiPredicate;

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
    
    
    /**
     * This method is responsible for validate the user data inputs against the app constraints
     *
     * @param user - User data to be audit
     * @return TRUE if data passed all the constraints
     * @throws UserDataIsInvalidException - Return this exception if the User Data not satisfied the constraints
     */
    private boolean isUserValid(User user) throws UserDataIsInvalidException {
        
        //User cannot be null
        if(user == null){throw new UserDataIsInvalidException("Please provide a not null object");}
        
        try{
            //USERNAME
            //Not null or Empty
            isNullOrEmpty("Username", user.getUsername());
            //No more than 20 characters
            isLengthValid("Username", 20, user.getUsername());
            //Must starts with alphanumeric char & dot/hyphen/underscore doesn't appears consecutively & alphanumeric end
            isPatternSatisfied("Username",
                               "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$",
                               user.getUsername(),
                               "It must start with an alphanumeric character; the following characters dot(.), hyphen(-), and underscore (_) cannot be consecutive; Finally alphanumeric character for ending.");
            
            //PASSWORD
            // Not Null or empty
            isNullOrEmpty("Password", user.getPassword());
            //No more than 255 characters
            isLengthValid("Password", 255, user.getUsername());
            //Must has a minimum of eight characters & at least one uppercase letter, one lowercase letter, one number and one special character
            isPatternSatisfied("Password",
                               "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                               user.getPassword(),
                               "Its must has a minimum of eight characters & at least one uppercase letter, one lowercase letter, one number and one special character");
            
            //EMAIL
            // Not null or empty
            isNullOrEmpty("Email", user.getEmail());
            //No more than 255 characters
            isLengthValid("Email", 255, user.getEmail());
            //Minimum two character before @, must contains @ and the domain (Doesn't validate if the domain is valid)
            isPatternSatisfied("Email",
                               "(?!.*\\.\\.)(^[^\\.][^@\\s]+@[^@\\s]+\\.[^@\\s\\.]+$)",
                               user.getEmail(),
                               "Must be a valid email address");
            
        }catch(UserDataIsInvalidException e){
            throw e;
        }
        
        //Return this if passed all the constraints
        return true;
    }
    
    /**
     * This method is part of isUserValid
     *
     * @param field - that we want to evaluate
     * @param strToEval - the string value from the field to be evaluated
     * @throws UserDataIsInvalidException - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isNullOrEmpty(String field,String strToEval) throws UserDataIsInvalidException {
        
        //Evaluates if the string passed is null or empty
        Predicate<String> isNullOrEmptyPredicate = str -> (str == null || str.trim().isEmpty());
        
        if(isNullOrEmptyPredicate.test(strToEval)) { throw new UserDataIsInvalidException(field+ ": empty or null");}
        }
    
    /**
     * This method is part of isUserValid
     *
     * @param field - that we want to evaluate
     * @param limit - to be evaluated with length of string
     * @param strToEval - the string value from the field to be evaluated
     * @throws UserDataIsInvalidException - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isLengthValid(String field, int limit, String strToEval) throws UserDataIsInvalidException {
       
        //Evaluates if the string passed is inside of the length range
        BiPredicate<String, Integer> eval = (str, length) -> str.length() > length;
    
        if(!eval.test(strToEval,20)){throw new UserDataIsInvalidException(field+ ": has more than "+limit+" characters");}
        
    }
    
    /**
     * This method is part of isUserValid
     *
     * @param field - that we want to evaluate
     * @param inputPattern - used to run the evaluation
     * @param strToEval - the string value from the field to be evaluated
     * @param exceptionMessage - Message in case of exception
     * @throws UserDataIsInvalidException - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isPatternSatisfied(String field, String inputPattern, String strToEval, String exceptionMessage) throws UserDataIsInvalidException {
        
        //Evaluates if the string passed satisfied the pattern passed
        BiPredicate<String, String> eval = ((str, pattern) -> {
            Pattern patternToCompile = Pattern.compile(pattern);
            Matcher patternValidation = patternToCompile.matcher(str);
            return patternValidation.matches();});
            
            if(!eval.test(strToEval, inputPattern)){ throw new UserDataIsInvalidException(field+": "+exceptionMessage);};
    }

    public User registerUser (User user) {
        user.setRole(User.Role.BASIC_USER);
        return userRepository.save(user);
    }

    public UserFavoriteRecipe addFavorite(String username, int recipeID) {
        User user = userRepository.findUserByUsername(username);
        Recipe recipe = recipeRepository.findById(recipeID)
                .orElseThrow(InvalidRequestException::new);
        Set<UserFavoriteRecipe> favorites = favoriteRecipeRepository.findByUserId(user.getId());

        UserFavoriteRecipe ufr = favorites.stream().filter(userFavoriteRecipe -> userFavoriteRecipe.getRecipe().getId() == recipeID)
                .findFirst()
                .orElseGet(UserFavoriteRecipe::new);

        /*
            prevent potentially duplicate favorite recipes from being inserted into our DB. If the user favorites the recipe once,
            it already exists as favorite or not favorite
         */
        if(ufr.getId() == 0) {
            ufr.setFavorite(true);
            ufr.setUser(user);
            ufr.setRecipe(recipe);
            return favoriteRecipeRepository.save(ufr);
        } else {
            ufr.setFavorite(true);
            return favoriteRecipeRepository.save(ufr);
        }
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

