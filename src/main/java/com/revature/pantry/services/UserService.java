package com.revature.pantry.services;


import com.revature.pantry.exceptions.*;
import com.revature.pantry.models.Recipe;
import com.revature.pantry.exceptions.UserDataIsInvalidException;
import com.revature.pantry.models.User;
import com.revature.pantry.repos.RecipeRepository;
import com.revature.pantry.repos.UserRepository;
import com.revature.pantry.web.dtos.Credentials;
import com.revature.pantry.web.dtos.RecipeDTO;
import com.revature.pantry.web.dtos.NewUserDTO;
import com.revature.pantry.web.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.*;
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
        if (user == null) {
            throw new AuthenticationException("You inputted an invalid username or password!");
        }
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    public User findUserById(int userId) {
        return userRepository.findUserById(userId);
    }

    /**
     * This method is responsible for validate the user data inputs against the app constraints
     *
     *
     * @return TRUE if data passed all the constraints
     * @throws UserDataIsInvalidException - Return this exception if the User Data not satisfied the constraints
     */

    private void isNullOrEmpty(String field, String strToEval) throws UserDataIsInvalidException {

        //Evaluates if the string passed is null or empty
        Predicate<String> isNullOrEmptyPredicate = str -> (str == null || str.trim().isEmpty());

        if (isNullOrEmptyPredicate.test(strToEval)) {
            throw new UserDataIsInvalidException(field + ": empty or null");
        }
    }

        /**
         * This method is part of isUserValid
         *
         * @param field     - that we want to evaluate
         * @param limit     - to be evaluated with length of string
         * @param strToEval - the string value from the field to be evaluated
         * @throws UserDataIsInvalidException - Exception returned if the strToEval doesn't pass the evaluation
         */
        private void isLengthValid(String field,int limit, String strToEval) throws UserDataIsInvalidException {

            //Evaluates if the string passed is inside of the length range
            BiPredicate<String, Integer> eval = (str, length) -> str.length() > length;

            if (!eval.test(strToEval, 20)) {
                throw new UserDataIsInvalidException(field + ": has more than " + limit + " characters");
            }
        }

        /**
         * This method is part of isUserValid
         *
         * @param field            - that we want to evaluate
         * @param inputPattern     - used to run the evaluation
         * @param strToEval        - the string value from the field to be evaluated
         * @param exceptionMessage - Message in case of exception
         * @throws UserDataIsInvalidException - Exception returned if the strToEval doesn't pass the evaluation
         */
        private void isPatternSatisfied (String field, String inputPattern, String strToEval, String exceptionMessage) throws
        UserDataIsInvalidException {

            //Evaluates if the string passed satisfied the pattern passed
            BiPredicate<String, String> eval = ((str, pattern) -> {
                Pattern patternToCompile = Pattern.compile(pattern);
                Matcher patternValidation = patternToCompile.matcher(str);
                return patternValidation.matches();
            });

            if (!eval.test(strToEval, inputPattern)) {
                throw new UserDataIsInvalidException(field + ": " + exceptionMessage);
            }
        }

        public User registerUser (NewUserDTO newUser){
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

        public boolean removeUser (String username, Credentials creds){

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

        public UserDTO addFavorite (RecipeDTO recipeDTO, String username){
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

        public UserDTO addFavorite ( int recipeId, String username){
            Optional<Recipe> _recipe = recipeRepository.findById(recipeId);
            Optional<User> _user = Optional.ofNullable(userRepository.findUserByUsername(username));
            UserDTO result = new UserDTO();

            if (_user.isPresent() && _recipe.isPresent()) {
                User user = _user.get();
                Recipe recipe = _recipe.get();

                user.addFavorite(recipe);
                recipe.addUser(user);
                userRepository.save(user);
                recipeRepository.save(recipe);
                result.setUsername(username);
                result.setFavorites(user.getFavorites());
                return result;
            } else {
                throw new InvalidRequestException();
            }
        }

        public UserDTO addFavorites (List < RecipeDTO > recipeDTO, String username){
            UserDTO savedFavoriteUser = null;
            for (RecipeDTO recipe : recipeDTO) {
                System.out.println(recipe);
                savedFavoriteUser = addFavorite(recipe, username);
            }

            return savedFavoriteUser;
        }

        public boolean removeFavoriteRecipe (String username,int recipeId){
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

        public Set<Recipe> getFavoriteRecipes (String username){
            Optional<User> _user = Optional.ofNullable(userRepository.findUserByUsername(username));

            if (_user.isPresent()) {
                return _user.get().getFavorites();
            } else {
                throw new InvalidRequestException();
            }
        }

        public void saveMealPlan (User user){
            userRepository.save(user);
        }
    }


