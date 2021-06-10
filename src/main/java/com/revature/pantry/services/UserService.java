package com.revature.pantry.services;


import com.revature.pantry.exceptions.*;
import com.revature.pantry.models.Recipe;
import com.revature.pantry.models.User;
import com.revature.pantry.repos.RecipeRepository;
import com.revature.pantry.repos.UserRepository;
import com.revature.pantry.web.dtos.Credentials;
import com.revature.pantry.web.dtos.RecipeDTO;
import com.revature.pantry.web.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.function.BiPredicate;

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
        if(user == null){
            throw new AuthenticationException("You inputted an invalid username or password!");
        }
        return userRepository.findUserByUsernameAndPassword(username, password);
    }


    /**
     * This method is responsible for validate the user data inputs against the app constraints
     *
     * @param user - User data to be audit
     * @return TRUE if data passed all the constraints
     * @throws UserDataIsInvalid - Return this exception if the User Data not satisfied the constraints
     */
    @Deprecated
    private boolean isUserValid(User user) throws UserDataIsInvalid {

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


        //Return this if passed all the constraints
        return true;
    }

    /**
     * This method is part of isUserValid
     *
     * @param field     - that we want to evaluate
     * @param strToEval - the string value from the field to be evaluated
     * @throws UserDataIsInvalid - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isNullOrEmpty(String field, String strToEval) throws UserDataIsInvalid {

        //Evaluates if the string passed is null or empty
        Predicate<String> isNullOrEmptyPredicate = str -> (str == null || str.trim().isEmpty());

        if (isNullOrEmptyPredicate.test(strToEval)) {
            throw new UserDataIsInvalid(field + ": empty or null");
        }
    }

    /**
     * This method is part of isUserValid
     *
     * @param field     - that we want to evaluate
     * @param limit     - to be evaluated with length of string
     * @param strToEval - the string value from the field to be evaluated
     * @throws UserDataIsInvalid - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isLengthValid(String field, int limit, String strToEval) throws UserDataIsInvalid {

        //Evaluates if the string passed is inside of the length range
        BiPredicate<String, Integer> eval = (str, length) -> str.length() > length;

        if (!eval.test(strToEval, 20)) {
            throw new UserDataIsInvalid(field + ": has more than " + limit + " characters");
        }

    }

    /**
     * This method is part of isUserValid
     *
     * @param field            - that we want to evaluate
     * @param inputPattern     - used to run the evaluation
     * @param strToEval        - the string value from the field to be evaluated
     * @param exceptionMessage - Message in case of exception
     * @throws UserDataIsInvalid - Exception returned if the strToEval doesn't pass the evaluation
     */
    private void isPatternSatisfied(String field, String inputPattern, String strToEval, String exceptionMessage) throws UserDataIsInvalid {

        //Evaluates if the string passed satisfied the pattern passed
        BiPredicate<String, String> eval = ((str, pattern) -> {
            Pattern patternToCompile = Pattern.compile(pattern);
            Matcher patternValidation = patternToCompile.matcher(str);
            return patternValidation.matches();
        });

        if (!eval.test(strToEval, inputPattern)) {
            throw new UserDataIsInvalid(field + ": " + exceptionMessage);
        }
        ;
    }

    public User registerUser(User user) {
        user.setRole(User.Role.BASIC_USER);
        return userRepository.save(user);
    }

    public boolean removeUser(String username, Credentials creds) {

        if (!username.equals(creds.getUsername())) {
            throw new InvalidRequestException("Submitted username does not match currently logged in user.");
        }

        User user = authenticate(creds.getUsername(), creds.getPassword());

        if (user == null) {
            throw new InvalidRequestException("Submitted password is incorrect.");
        } else {
            userRepository.delete(user);
            return true;
        }
    }

    public UserDTO addFavorite(RecipeDTO recipeDTO, String username) {
        Optional<Recipe> _recipe = recipeRepository.findByUrl(recipeDTO.getUrl());
        Optional<User> _user = Optional.ofNullable(userRepository.findUserByUsername(username));
        Recipe recipe = null;
        UserDTO userDTO = new UserDTO();

        if (!_recipe.isPresent()) {
            recipe = new Recipe();
            recipe.setLabel(recipeDTO.getLabel());
            recipe.setImage(recipeDTO.getImage());
            recipe.setUrl(recipeDTO.getUrl());
            recipe.setCalories(recipeDTO.getCalories());
            recipe.setYield(recipeDTO.getYield());
            recipe = recipeRepository.save(recipe);
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
            throw new InvalidRequestException("Your request is invalid!");
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

        if(_user.isPresent() && _recipe.isPresent()) {
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

        if(_user.isPresent()) {
           return _user.get().getFavorites();
        } else {
            throw new InvalidRequestException();
        }
    }
}

