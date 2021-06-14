package com.revature.pantry.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pantry.models.Recipe;
import com.revature.pantry.models.User;
import com.revature.pantry.web.dtos.MealPlanDTO;
import com.revature.pantry.web.dtos.MealTimeDTO;
import com.revature.pantry.web.security.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@ExtendWith({SpringExtension.class})
public class MealPlanControllerIntegrationTest {

    private MockMvc mockMvc;
    private WebApplicationContext webContext;
    private TokenGenerator tokenGenerator;
    private ObjectMapper mapper = new ObjectMapper();
    private User mockAdminUser = new User();
    private String token;

    @Autowired
    public MealPlanControllerIntegrationTest(WebApplicationContext webContext, TokenGenerator tokenGenerator) {
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
    public void test_savePlanWithValidData() throws Exception {
        MealTimeDTO mealTimeDTO = new MealTimeDTO();
        mealTimeDTO.setTime("lunch");
        Recipe recipe = new Recipe("chicken", "https://www.google.com", "https://img2.mashed.com/img/gallery/mistakes-youre-making-when-cooking-a-heritage-chicken/cook-heritage-chicken-low-and-slow-1597170193.jpg",
                200, 3);
        recipe.setId(1);
        mealTimeDTO.setRecipe(recipe);

        MealPlanDTO mealPlanDTO = new MealPlanDTO();
        mealPlanDTO.setDayPlanList(Collections.singletonList(mealTimeDTO));
        mealPlanDTO.setUserId(2);

        String json = mapper.writeValueAsString(mealPlanDTO);

        System.out.println(json);

        mockMvc.perform(put("/meals/save/plan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void test_SavePlanWith() throws Exception{
        mockMvc.perform(put("/meals/save/plan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void test_findPlanWithValidData() throws Exception {

        //valid userid with import.sql
        mockMvc.perform(get("/meals/find?userId=2"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void test_findPlanWithinValidData() throws Exception {

        mockMvc.perform(get("/meals/find?userId=asdf"))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }


}
