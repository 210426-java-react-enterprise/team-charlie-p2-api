package com.revature.pantry.services;

import com.revature.pantry.models.User;
import com.revature.pantry.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public User registerUser (User user) {
        user.setRole(User.Role.BASIC_USER);
        return userRepository.save(user);
    }

}
