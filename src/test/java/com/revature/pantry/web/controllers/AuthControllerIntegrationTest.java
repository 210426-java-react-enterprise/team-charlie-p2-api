package com.revature.pantry.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pantry.models.User;
import com.revature.pantry.web.dtos.Credentials;
import com.revature.pantry.web.security.TokenGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith({SpringExtension.class})
public class AuthControllerIntegrationTest {

    private MockMvc mockMvc;
    private WebApplicationContext webContext;
    private TokenGenerator tokenGenerator;
    private String token;
    private User mockAdminUser = new User();
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public AuthControllerIntegrationTest(WebApplicationContext webContext, TokenGenerator tokenGenerator) {
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
    public void test_authWithValidCredentials() throws Exception {
        //mock user imported using an import.sql
        Credentials credentials = new Credentials();
        credentials.setUsername("username");
        credentials.setPassword("password");

        String json = mapper.writeValueAsString(credentials);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .header("Content-Type", "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"));
    }

    @Test
    public void test_authWithInvalidCredentials() throws Exception {
        Credentials credentials = new Credentials();
        credentials.setUsername("username");
        credentials.setPassword("wrongPassword");

        String json = mapper.writeValueAsString(credentials);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError())
                .andExpect(header().doesNotExist("Authorization"));

    }

}
