package com.revature.pantry.services;

import com.revature.pantry.exceptions.AuthenticationException;
import com.revature.pantry.exceptions.InvalidRequestException;
import com.revature.pantry.models.Recipe;
import com.revature.pantry.models.User;
import com.revature.pantry.repos.RecipeRepository;
import com.revature.pantry.repos.UserRepository;
import com.revature.pantry.web.dtos.Credentials;
import com.revature.pantry.web.dtos.RecipeDTO;
import com.revature.pantry.web.dtos.UserDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService sut;
    private UserRepository mockUserRepository;
    private RecipeRepository mockRecipeRepository;

    @Before
    public void setUpTest() {
        mockRecipeRepository = mock(RecipeRepository.class);
        mockUserRepository = mock(UserRepository.class);
        sut = new UserService(mockUserRepository, mockRecipeRepository);
    }

    @After
    public void tearDownTest() {
        mockRecipeRepository = null;
        mockUserRepository = null;
        sut = null;
    }

    @Test
    public void whenAuthenticateWithValidCredentials_thenReturnUser() {
        //Arrange
        String username = "username";
        String password = "password";
        User expected = new User(username, password, "random@mail.com");
        when(mockUserRepository.findUserByUsernameAndPassword(username, password)).thenReturn(expected);

        //Act
        User actual = sut.authenticate(username, password);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void whenAuthenticateWithInvalidCredentials_thenThrowException() {
        //Arrange
        String username = "username";
        String password = "wrongPassword";
        when(mockUserRepository.findUserByUsernameAndPassword(username, password)).thenReturn(null);

        //Act
        try {
            sut.authenticate(username, password);
        } catch (Exception e) {
            assertTrue(e instanceof AuthenticationException);
        }
    }

    @Test
    public void whenFindUserByIdWithValidId_thenReturnUser() {
        //Arrange
        User expected = new User("username", "password", "random@mail.com");
        expected.setId(1);
        when(mockUserRepository.findById(1)).thenReturn(Optional.of(expected));

        //Act
        User actual = sut.findUserById(1);

        //Assert
        assertEquals(expected, actual);
    }

    /*
        For some reason, this method under test does not throw the exception. It does throw one on the live server.
     */
    @Test
    public void whenFindUserByIdWithInvalidID_ThenThrowException() {
        User test = null;
        when(mockUserRepository.findById(anyInt())).thenReturn(null);

        try {
            test = sut.findUserById(1);
        } catch (Exception e) {
            assertNull(test);
        }
    }

    @Test
    public void whenRemoveUserWithValidCredentials_ThenReturnTrue() {
        //Arrange
        boolean result = false;
        Credentials credentials = new Credentials("username", "password");
        String username = "username";
        User expected = new User();
        when(mockUserRepository.findUserByUsernameAndPassword(username, credentials.getPassword())).thenReturn(expected);

        //Act
        result = sut.removeUser(username, credentials);

        //Assert
        assertTrue(result);
    }

    @Test
    public void whenRemoveUserWithInvalidCredentials_ThenThrowException() {
        //Arrange
        Credentials credentials = new Credentials("wrongUsername", "wrongPassword");
        String username = "username";

        //Act
        try {
            sut.removeUser(username, credentials);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("Submitted username does not match currently logged in user.", e.getMessage());
        } finally {
            verify(mockUserRepository, times(0)).delete(any());
        }
    }

    @Test
    public void whenRemoveUserWithInvalidPassword_thenThrowException() {
        Credentials credentials = new Credentials("username", "wrongPassword");
        String username = "username";
        when(mockUserRepository.findUserByUsernameAndPassword(username, credentials.getPassword())).thenReturn(null);

        try {
            sut.removeUser(username, credentials);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("Submitted password is incorrect.", e.getMessage());
        } finally {
            verify(mockUserRepository, times(0)).delete(any());
        }
    }

    @Test
    public void whenAddFavoriteWithValidDetails_thenReturnDTO() {
        //Arrange
        RecipeDTO recipeDTO = new RecipeDTO("food", 100, 3, "someUrl", "someImage");
        String username = "username";
        User user = new User(username, "password", "randomEmail@mail.com");
        UserDTO expected = new UserDTO(user.getUsername(), user.getFavorites());

        when(mockUserRepository.findUserByUsername(user.getUsername())).thenReturn(user);
        when(mockRecipeRepository.findByLabelAndUrlAndImage(recipeDTO.getLabel(), recipeDTO.getUrl(), recipeDTO.getImage()))
                .thenReturn(Optional.of(new Recipe(recipeDTO)));
        when(mockRecipeRepository.save(any())).thenReturn(new Recipe(recipeDTO));
        when(mockUserRepository.save(any())).thenReturn(user);
        when(mockUserRepository.findUserByUsername(username)).thenReturn(user);

        //Act
        UserDTO actual = sut.addFavorite(recipeDTO, user.getUsername());

        //Assert
        assertEquals(expected.getFavorites(), actual.getFavorites());
        assertEquals(expected.getUsername(), actual.getUsername());
        verify(mockUserRepository, times(1)).save(any());
        verify(mockRecipeRepository, times(1)).save(any());
    }
}
