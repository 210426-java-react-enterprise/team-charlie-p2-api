package com.revature.pantry.web.controllers;

import com.revature.pantry.models.*;
import com.revature.pantry.web.security.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

@SpringBootTest
@ExtendWith({SpringExtension.class})
public class ApiDocControllerIntegrationTestSuite {

    private MockMvc mockMvc;
    private WebApplicationContext webContext;

    @Autowired
    public ApiDocControllerIntegrationTestSuite(WebApplicationContext webContext, TokenGenerator tokenGenerator) {
        this.webContext = webContext;
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    public void test_getDocumentation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/docs"))
               .andExpect(MockMvcResultMatchers.status().is(303))
               .andExpect(MockMvcResultMatchers.header().doesNotExist("Authorization"))
               .andDo(MockMvcResultHandlers.print())
               .andReturn();
        }


}
