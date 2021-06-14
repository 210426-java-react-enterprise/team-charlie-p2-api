package com.revature.pantry.controllers;

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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTestSuite {

    private MockMvc mockMvc;
    private WebApplicationContext webContext;
    private TokenGenerator tokenGenerator;
    private String token;

    @Autowired
    public UserControllerIntegrationTestSuite(WebApplicationContext webContext, TokenGenerator tokenGenerator) {
        this.webContext = webContext;
        this.tokenGenerator = tokenGenerator;
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
    public void test_getAllUsers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/users")
                                                   .header("Authorization", token)
                                                   .header("Content-Type", "application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}
