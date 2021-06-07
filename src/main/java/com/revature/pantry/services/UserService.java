package com.revature.pantry.services;

import com.revature.pantry.exceptions.InvalidRequestException;
import com.revature.pantry.models.Recipe;
import com.revature.pantry.models.User;
import com.revature.pantry.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User authenticate (String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    public User registerUser (User user) {
        user.setRole(User.Role.BASIC_USER);
        return userRepository.save(user);
    }

    public void addFavorite(int id, Recipe recipe) {
        User user = userRepository.findById(id)
                .orElseThrow(InvalidRequestException::new);
        user.getFavoriteRecipes().add(recipe);
        userRepository.save(user);
    }

    public List<Recipe> getFavoriteRecipes(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(InvalidRequestException::new);
        return user.getFavoriteRecipes();
    }

    public void removeFavorite(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(InvalidRequestException::new);

        user.setFavoriteRecipes(user.getFavoriteRecipes()
                    .stream()
                    .filter(recipe -> recipe.getId() != id)
                    .collect(Collectors.toList()));
        userRepository.save(user);
    }

}
