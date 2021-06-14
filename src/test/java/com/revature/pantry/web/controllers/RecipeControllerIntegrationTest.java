package com.revature.pantry.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pantry.models.User;
import com.revature.pantry.web.security.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RecipeControllerIntegrationTest {

    private MockMvc mockMvc;
    private WebApplicationContext webContext;
    private TokenGenerator tokenGenerator;
    private String token;
    private User mockAdminUser = new User();
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public RecipeControllerIntegrationTest(WebApplicationContext webContext, TokenGenerator tokenGenerator) {
        this.webContext = webContext;
        this.tokenGenerator = tokenGenerator;
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
        mockAdminUser.setUsername("username");
        mockAdminUser.setPassword("password");
        mockAdminUser.setEmail("mock@admins.net");
        mockAdminUser.setRole(User.Role.ADMIN);
        this.token = tokenGenerator.createJwt(mockAdminUser);
    }


    @Test
    public void test_SearchWithValidQuery() throws Exception {
        String q = "chicken";

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/recipe/search?q=%s", q)))
        .andExpect(status().isOk())
        .andDo(print())
        .andReturn();

    }

    @Test
    public void test_SearchWithInvalidQuery() throws Exception {
        String q = "12341234";

        mockMvc.perform(get(String.format("/recipe/search?q=%s", q)))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }
}