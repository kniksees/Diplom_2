package site.stellarburgers.nomoreparties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.stellarburgers.nomoreparties.model.User;
import site.stellarburgers.nomoreparties.model.UserCredentials;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static site.stellarburgers.nomoreparties.UserClient.*;

public class UserTest {

    private Response responseAboutRegisterWithCorrectUniqueData;
    private User user;
    private UserCredentials userCredentials;

    @Before
    public void setUpUser() {

        user = User.getRandomUser();
        userCredentials = new UserCredentials(user.getEmail(), user.getPassword());
        responseAboutRegisterWithCorrectUniqueData = createUser(user);
    }

    @After
    public void deleteCourier() {

        if (responseAboutRegisterWithCorrectUniqueData.body().jsonPath().getString("accessToken") != null) {
            Response responseAboutDelete = deleteUser(responseAboutRegisterWithCorrectUniqueData);
            assertEquals(responseAboutDelete.statusCode(), 202);
        }
    }

    @Test
    public void createUniqueUserTest() {

        assertEquals(responseAboutRegisterWithCorrectUniqueData.statusCode(), SC_OK);
    }

    @Test
    public void createExistingUserTest() {

        Response responseAboutRegisterWithExistingData = createUser(user);

        assertEquals(responseAboutRegisterWithExistingData.statusCode(), SC_FORBIDDEN);
    }

    @Test
    public void createUserWithOutPasswordTest() {

        User user = User.getRandomUserWithOutPassword();
        responseAboutRegisterWithCorrectUniqueData = createUser(user);

        assertEquals(responseAboutRegisterWithCorrectUniqueData.statusCode(), SC_FORBIDDEN);
    }

    @Test
    public void loginUserWithCorrectDataTest() {

        Response responseAboutLoginWithCorrectData = loginUserWithCorrectData(userCredentials);

        assertEquals(responseAboutLoginWithCorrectData.statusCode(), SC_OK);
    }

    @Test
    public void loginUserWithIncorrectDataTest() {

        userCredentials = new UserCredentials(user.getEmail() + "1", user.getPassword() + "1");
        Response responseAboutLoginWithIncorrectData = loginUserWithCorrectData(userCredentials);

        assertEquals(responseAboutLoginWithIncorrectData.statusCode(), SC_UNAUTHORIZED);
    }

    @Test
    public  void changeUserDataTestWithAuthorization() {

        Response responseAboutChangeUserDataWithAuthorization = changeUserDataWithAuthorization(responseAboutRegisterWithCorrectUniqueData, User.getRandomUser());

        assertEquals(responseAboutChangeUserDataWithAuthorization.statusCode(), SC_OK);
    }

    @Test
    public  void changeUserDataTestWithOutAuthorization() {

        Response responseAboutChangeUserDataWithOutAuthorization = changeUserDataWithOutAuthorization(User.getRandomUser());

        assertEquals(responseAboutChangeUserDataWithOutAuthorization.statusCode(), SC_UNAUTHORIZED);
    }


}
