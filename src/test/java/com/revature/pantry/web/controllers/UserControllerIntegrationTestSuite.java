package com.revature.pantry.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pantry.models.User;
import com.revature.pantry.web.dtos.*;
import com.revature.pantry.web.security.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTestSuite {

    private MockMvc mockMvc;
    private TokenGenerator tokenGenerator;
    private String token;
    private User mockAdminUser = new User();
    private WebApplicationContext webContext;
    private ObjectMapper mapper;

    @Autowired
    public UserControllerIntegrationTestSuite(TokenGenerator tokenGenerator, WebApplicationContext webContext, ObjectMapper mapper) {
        this.tokenGenerator = tokenGenerator;
        this.webContext = webContext;
        this.mapper = mapper;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
        mockAdminUser.setUsername("username");
        mockAdminUser.setPassword("password");
        mockAdminUser.setEmail("mock@admins.net");
        mockAdminUser.setRole(User.Role.ADMIN);
        token = tokenGenerator.createJwt(mockAdminUser);
    }

    @Test
    public void test_registerUser() throws Exception {
        Registration user = new Registration();
        user.setUsername("swekevin");
        user.setPassword("P4ssword!");
        user.setEmail("swekevin@gmail.com");

        String json = mapper.writeValueAsString(user);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                                                   .header("Authorization", token)
                                                   .header("Content-Type", "application/json")
                                                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                   .content(json))
                    .andDo(print())
                    .andExpect(status().is(201))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void test_registerUser_BadUsernameAndPassordAndEmail() throws Exception {
        Registration user = new Registration();
        user.setUsername("swekevin!");
        user.setPassword("password");
        user.setEmail("swekevin@");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                                                   .header("Authorization", token)
                                                   .header("Content-Type", "application/json")
                                                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                   .content(json))
                    .andDo(print())
                    .andExpect(status().is(400))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void test_deleteUser_WithSameUser() throws Exception {
        Credentials creds = new Credentials();
        creds.setUsername("mock-user");
        creds.setPassword("P4ssword!");

        String json = mapper.writeValueAsString(creds);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/user/account")
                                                   .header("Authorization", token)
                                                   .header("Content-Type", "application/json")
                                                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                   .content(json))
                    .andDo(print())
                    .andExpect(status().is(400))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void test_deleteUser_WithDifferentUser() throws Exception {
        Credentials creds = new Credentials();
        creds.setUsername("swekevin");
        creds.setPassword("P4ssword!");

        String json = mapper.writeValueAsString(creds);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/user/account")
                                                   .header("Authorization", token)
                                                   .header("Content-Type", "application/json")
                                                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                   .content(json))
                    .andDo(print())
                    .andExpect(status().is(400))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }


    @Test
    public void test_favoriteRecipe() throws Exception {

        Registration user = new Registration();
        user.setUsername("mock-admin");
        user.setPassword("P4ssword!");
        user.setEmail("swekevin1@gmail.com");

        ObjectMapper mapper = new ObjectMapper();
        String registerjson = mapper.writeValueAsString(user);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                                                   .header("Authorization", token)
                                                   .header("Content-Type", "application/json")
                                                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                   .content(registerjson));
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setLabel("Recipe");
        recipeDTO.setYield(4);
        recipeDTO.setUrl("http://recipe.com");
        recipeDTO.setImage("http://img.com");
        recipeDTO.setCalories(4000);

        List<RecipeDTO> recipes = new ArrayList<>();
        recipes.add(recipeDTO);
        String json = mapper.writeValueAsString(recipes);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/favorites")
                                                   .header("Authorization", token)
                                                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                   .content(json))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }


    @Test
    public void test_getFavoritesWithValidToken() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/user/favorite")
        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Authorization"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_getFavoritesWithInvalidToken() throws Exception {
        User wrongUser = new User();
        wrongUser.setUsername("wrongUsername");
        wrongUser.setPassword("WrongPassword");
        wrongUser.setRole(User.Role.BASIC_USER);
        wrongUser.setEmail("something@gmail.com");
        String wrongToken = tokenGenerator.createJwt(wrongUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/favorite")
        .header("Authorization", wrongToken))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Authorization"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void test_removeFavoriteWithValidToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/favorite/1")
        .header("Authorization", token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Authorization"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void  test_removeFavoriteWithInvalidToken() throws Exception {
        User wrongUser = new User();
        wrongUser.setUsername("wrongUsername");
        wrongUser.setPassword("WrongPassword");
        wrongUser.setRole(User.Role.BASIC_USER);
        wrongUser.setEmail("something@gmail.com");
        String wrongToken = tokenGenerator.createJwt(wrongUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/favorite/1")
                .header("Authorization", wrongToken))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Authorization"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    //    @Test
    public void test_updateTimesPrepared() throws Exception {
        User mockJaneDoe = new User();
        mockJaneDoe.setUsername("jane.doe");
        mockJaneDoe.setRole(User.Role.BASIC_USER);
        String myToken = tokenGenerator.createJwt(mockJaneDoe);

        FavoriteDTO favoriteDTO = new FavoriteDTO();
        favoriteDTO.setTimesPrepared(5);
//        favoriteDTO.setCalories(4000);
//        favoriteDTO.setImage("http://img.com");
//        favoriteDTO.setLabel("Recipe");
//        favoriteDTO.setUrl("http://recipe.com");
        favoriteDTO.setRecipeId(1);
        String json = mapper.writeValueAsString(favoriteDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.patch("/user/favorite")
                                                   .header("Authorization", myToken)
                                                   .header("Content-Type", "application/json")
                                                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                   .content(json))
                    .andDo(print())
                    .andExpect(status().isOk());
    }


}
