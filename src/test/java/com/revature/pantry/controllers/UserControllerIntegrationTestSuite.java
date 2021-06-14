package com.revature.pantry.controllers;

import com.fasterxml.jackson.databind.*;
import com.revature.pantry.models.*;
import com.revature.pantry.web.dtos.*;
import com.revature.pantry.web.security.*;
import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTestSuite {

    private MockMvc mockMvc;
    private WebApplicationContext webContext;
    private TokenGenerator tokenGenerator;
    private String token;
    private ObjectMapper mapper;

    @Autowired
    public UserControllerIntegrationTestSuite(WebApplicationContext webContext, TokenGenerator tokenGenerator, ObjectMapper mapper) {
        this.webContext = webContext;
        this.tokenGenerator = tokenGenerator;
        this.mapper = mapper;
    }


    @BeforeEach
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
        User mockAdminUser = new User();
        mockAdminUser.setId(100);
        mockAdminUser.setUsername("mock-admin");
        mockAdminUser.setRole(User.Role.ADMIN);
        this.token = tokenGenerator.createJwt(mockAdminUser);
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
    public void test_getAllUsers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/users")
                                                   .header("Authorization", token)
                                                   .header("Content-Type", "application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
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
        user.setEmail("swekevin@gmail.com");

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
