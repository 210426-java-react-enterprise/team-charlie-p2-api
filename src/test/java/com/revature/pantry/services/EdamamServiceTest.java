package com.revature.pantry.services;

import com.revature.pantry.web.dtos.*;
import org.junit.*;
import org.springframework.http.*;
import org.springframework.web.client.*;

import java.util.*;

import static org.mockito.Mockito.*;

public class EdamamServiceTest {


    private EdamamService sut;
    private RestTemplate mockRestTemplate;


    @Before
    public void setUp(){
        mockRestTemplate = mock(RestTemplate.class);
        sut = new EdamamService(mockRestTemplate);

    }

//    @After
//    public void tearDown(){
//        //tear down every mock or sut after each test run
//        mockRestTemplate = null;
//        sut = null;
//    }

    @Test
    public void test_getRecipesFromEdamam(){
        //Arrange
        RecipeDTO recipeDTO = new RecipeDTO("Recipe Label", 400, 4, "https://recipe.com", "https://image.url");
        Hit hit1 = new Hit(recipeDTO, true, true);
        List<Hit> hits = new ArrayList<Hit>();
        hits.add(hit1);
        Response response = new Response("chicken", 1, 2, 3, true, hits);

        String query = "https://api.edamam.com/search?q=chicken&app_id=appid&app_key=appkey";

        ResponseEntity mockResponseEntity = mock(ResponseEntity.class);
        ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.OK);

        when(mockRestTemplate.getForEntity(any(), any())).thenReturn(responseEntity);
        when(mockResponseEntity.getBody()).thenReturn(response);



        //Act
        List<RecipeDTO> listRecipes = sut.getRecipesFromEdamam(query);

        //Assert
        verify(mockRestTemplate, times(1)).getForEntity(any(), any()).getBody();

    }

    @Test
    public void test_isIngredientValid(){
        //Arrange
        //Act
        //Assert

    }

}
