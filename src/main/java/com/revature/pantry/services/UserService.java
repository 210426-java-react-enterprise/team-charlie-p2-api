package com.revature.pantry.services;

import com.revature.pantry.models.User;
import com.revature.pantry.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import java.util.function.BiPredicate;


@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User authenticate (String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username, password);
    }
    
    
    
    /**
     * This method is responsible for validate the user data inputs against the app constraints
     * @param user - User data to be audit
     * @return TRUE if data passed all the constraints / FALSE if not passed the constraints
     */
    private boolean isUserValid(User user){
        BiPredicate<String, Integer> isLengthValid = (str, lenght) -> str.length() <= lenght;
        BiPredicate<String, String> isPatternSatisfied = (str, pattern) -> {
            Pattern patternToCompile = Pattern.compile(pattern);
            Matcher patternValidation = patternToCompile.matcher(str);
            return patternValidation.matches();
        };
        
        if(user == null) return false;
        //username >> Start with alphanumeric char, dot/hyphen/underscore doesn't appears consecutively, alphanumeric end no more than 20 characters
        if(isLengthValid.test(user.getUsername(),20) || isPatternSatisfied.test(user.getUsername(), "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$"));
        //password >Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
        if(isLengthValid.test(user.getPassword(),255) || isPatternSatisfied.test(user.getPassword(), "\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\""));
        //email >> Minimum two character before @, must contains @ and the domain (Doesn't validate if the domain is valid)
        if(isLengthValid.test(user.getEmail(),255) || isPatternSatisfied.test(user.getEmail(), "(?!.*\\.\\.)(^[^\\.][^@\\s]+@[^@\\s]+\\.[^@\\s\\.]+$)"));
        return true;
    }
    
    is 
    

}
