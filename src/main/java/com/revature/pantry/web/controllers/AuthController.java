package com.revature.pantry.web.controllers;

import com.revature.pantry.models.User;
import com.revature.pantry.services.UserService;
import com.revature.pantry.web.dtos.Credentials;
import com.revature.pantry.web.dtos.UserDTO;
import com.revature.pantry.web.security.JwtConfig;
import com.revature.pantry.web.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private TokenGenerator tokenGenerator;
    private JwtConfig jwtConfig;
    private UserService userService;

    @Autowired
    public AuthController (TokenGenerator tokenGenerator, JwtConfig jwtConfig, UserService userService) {
        this.tokenGenerator = tokenGenerator;
        this.jwtConfig = jwtConfig;
        this.userService = userService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public UserDTO authenticate(@RequestBody Credentials credentials, HttpServletResponse response) {
        User user = userService.authenticate(credentials.getUsername(), credentials.getPassword());
        String jwt = tokenGenerator.createJwt(user);
        response.setHeader(jwtConfig.getHeader(), jwt);
        //Once they log in, pulls in current data about the user
        return new UserDTO(user);
    }

}
