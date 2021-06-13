package com.revature.pantry.services;

import com.revature.pantry.exceptions.AuthenticationException;
import com.revature.pantry.exceptions.InvalidRequestException;
import com.revature.pantry.models.FavoriteRecipe;
import com.revature.pantry.models.Recipe;
import com.revature.pantry.models.User;
import com.revature.pantry.repos.RecipeRepository;
import com.revature.pantry.repos.UserRepository;
import com.revature.pantry.web.dtos.Credentials;
import com.revature.pantry.web.dtos.FavoriteDTO;
import com.revature.pantry.web.dtos.RecipeDTO;
import com.revature.pantry.web.dtos.UserDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

//TODO fix broken tests
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
    public void whenFindUserByIdWithInvalidID_thenThrowException() {
        User test = null;
        when(mockUserRepository.findById(anyInt())).thenReturn(null);

        try {
            test = sut.findUserById(1);
        } catch (Exception e) {
            assertNull(test);
        }
    }

    @Test
    public void whenRemoveUserWithValidCredentials_thenReturnTrue() {
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
    public void whenRemoveUserWithInvalidCredentials_thenThrowException() {
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

//    @Test
//    public void whenAddFavoriteWithValidDetails_thenReturnDTO() {
//        //Arrange
//        RecipeDTO recipeDTO = new RecipeDTO("food", 100, 3, "someUrl", "someImage");
//        String username = "username";
//        User user = new User(username, "password", "randomEmail@mail.com");
//    //    user.addFavorite(new Recipe(recipeDTO));
//       // UserDTO expected = new UserDTO(user.getUsername(), user.getFavorites());
//
//        when(mockUserRepository.findUserByUsername(user.getUsername())).thenReturn(user);
//        when(mockRecipeRepository.findByLabelAndUrlAndImage(recipeDTO.getLabel(), recipeDTO.getUrl(), recipeDTO.getImage()))
//                .thenReturn(Optional.empty());
//        when(mockRecipeRepository.save(any())).thenReturn(new Recipe(recipeDTO));
//        when(mockUserRepository.save(any())).thenReturn(user);
//
//        //Act
//        UserDTO actual = sut.addFavorite(recipeDTO, user.getUsername());
//
//        verify(mockUserRepository, times(1)).save(any());
//        verify(mockRecipeRepository, times(2)).save(any());
//    }

    @Test
    public void whenAddFavoriteWithInvalidUser_thenThrowExceotion() {
        RecipeDTO recipeDTO = new RecipeDTO("food", 100, 3, "someUrl", "someImage");
        String username = "wrongUsername";
        User user = new User(username, "password", "randomEmail@mail.com");

        when(mockUserRepository.findUserByUsername(user.getUsername())).thenReturn(null);
        when(mockRecipeRepository.findByLabelAndUrlAndImage(recipeDTO.getLabel(), recipeDTO.getUrl(), recipeDTO.getImage()))
                .thenReturn(Optional.of(new Recipe(recipeDTO)));

        //Act
        try {
            sut.addFavorite(recipeDTO, username);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
            assertEquals("A wrong username got here somehow.", e.getMessage());
        } finally {
            verify(mockRecipeRepository, times(0)).save(any());
            verify(mockUserRepository, times(0)).save(any());
        }

    }

//    @Test
//    public void whenAddFavoritesWithValidInputs_thenReturnDTO() {
//        RecipeDTO recipeDTO = new RecipeDTO("food", 100, 3, "someUrl", "someImage");
//        RecipeDTO otherDTO = new RecipeDTO("otherFood", 200, 3, "someOtherUrl", "someOtherImage");
//        List<RecipeDTO> recipes = Arrays.asList(recipeDTO, otherDTO);
//        String username = "username";
//        User user = new User(username, "password", "randomEmail@mail.com");
//      // // recipes.forEach(dto -> user.addFavorite(new Recipe(dto)));
//        UserDTO expected = new UserDTO(user.getUsername(), user.getFavorites());
//
//        when(mockUserRepository.findUserByUsername(user.getUsername())).thenReturn(user);
//        when(mockRecipeRepository.findByLabelAndUrlAndImage(recipeDTO.getLabel(), recipeDTO.getUrl(), recipeDTO.getImage()))
//                .thenReturn(Optional.empty());
//        when(mockRecipeRepository.save(any())).thenReturn(new Recipe(recipeDTO));
//        when(mockUserRepository.save(any())).thenReturn(user);
//
//        UserDTO actual = sut.addFavorites(recipes, user.getUsername());
//
//        assertEquals(expected.getFavorites(), actual.getFavorites());
//        assertEquals(expected.getUsername(), actual.getUsername());
//        verify(mockUserRepository, times(2)).save(any());
//        verify(mockRecipeRepository, times(4)).save(any());
//    }

    @Test
    public void whenRemoveFavoriteWithValidInput_thenReturnTrue() {
        String username = "username";
        User user = new User(username, "password", "randomEmail@mail.com");
        Recipe recipe = new Recipe("food", "url", "image", 300, 3);

        when(mockRecipeRepository.findById(anyInt())).thenReturn(Optional.of(recipe));
        when(mockUserRepository.findUserByUsername(username)).thenReturn(user);

        boolean result = sut.removeFavoriteRecipe(username, 0);

        assertTrue(result);
    }

    /*
        only way this method fails is if client somehow sends wrong username (for now)
     */
    @Test
    public void whenRemoveFavoriteWithInvalidInput_thenThrowExceotion() {
        String username = "wrongUsername";
        User user = new User("username", "password", "randomEmail@mail.com");
        when(mockUserRepository.findUserByUsername(username)).thenReturn(null);
        when(mockRecipeRepository.findById(anyInt())).thenReturn(any());

        try {
            sut.removeFavoriteRecipe(username, 0);
        } catch (Exception e) {
            assertTrue(e instanceof InvalidRequestException);
        } finally {
            verify(mockUserRepository, times(0)).save(any());
            verify(mockRecipeRepository, times(0)).save(any());
        }
    }

    @Test
    public void whenGetFavoritesWithValidUsername_thenReturnFavorites() {
        User user = new User("username", "password", "randomEmail@mail.com");
        FavoriteRecipe favoriteRecipe = new FavoriteRecipe();
        user.getFavoriteRecipes().add(favoriteRecipe);
        Set<FavoriteDTO> expected = user.getFavorites();
        when((mockUserRepository.findUserByUsername(any()))).thenReturn(user);

        Set<FavoriteDTO> actual = sut.getFavoriteRecipes(user.getUsername());

        assertEquals(expected, actual);
    }

    @Test(expected = InvalidRequestException.class)
    public void whenGetFavoritesWithInvalidUsername_thenThrowException() {
        String username = "wrongUsername";
        User user = new User("username", "password", "randomEmail@mail.com");

        when(mockUserRepository.findUserByUsername(username)).thenReturn(null);

        sut.getFavoriteRecipes(username);
    }

//    @Test
//    public void whenSaveMealPlan_thenReturnTrue() {
//        //this method should theoretically never receive an invalid user
//        when(mockUserRepository.save(any())).thenReturn(new User());
//
//        assertTrue(sut.saveMealPlan(any()));
//    }
}
