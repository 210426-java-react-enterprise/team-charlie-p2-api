package com.revature.pantry.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pantry.models.User;
import com.revature.pantry.web.security.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTestSuite {

    private MockMvc mockMvc;
    private TokenGenerator tokenGenerator;
    private String token;
    private User mockAdminUser = new User();
    private WebApplicationContext webContext;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public UserControllerIntegrationTestSuite(TokenGenerator tokenGenerator, WebApplicationContext webContext) {
        this.tokenGenerator = tokenGenerator;
        this.webContext = webContext;
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

}
