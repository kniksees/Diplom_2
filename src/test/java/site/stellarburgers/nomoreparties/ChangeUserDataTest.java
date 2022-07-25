package site.stellarburgers.nomoreparties;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.stellarburgers.nomoreparties.model.User;
import site.stellarburgers.nomoreparties.model.UserCredentials;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static site.stellarburgers.nomoreparties.UserClient.*;

public class ChangeUserDataTest {

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
            assertEquals(responseAboutDelete.statusCode(), SC_ACCEPTED);
        }
    }

    @Test
    public  void changeUserDataWithAuthorizationTest() {
        Response responseAboutChangeUserDataWithAuthorization = changeUserDataWithAuthorization(responseAboutRegisterWithCorrectUniqueData, User.getRandomUser());

        assertEquals(responseAboutChangeUserDataWithAuthorization.statusCode(), SC_OK);
        assertEquals(responseAboutChangeUserDataWithAuthorization.statusLine(), "HTTP/1.1 " + SC_OK + " OK");
    }

    @Test
    public  void changeUserDataWithOutAuthorizationTest() {
        Response responseAboutChangeUserDataWithOutAuthorization = changeUserDataWithOutAuthorization(User.getRandomUser());

        assertEquals(responseAboutChangeUserDataWithOutAuthorization.statusCode(), SC_UNAUTHORIZED);
        assertEquals(responseAboutChangeUserDataWithOutAuthorization.statusLine(), "HTTP/1.1 " + SC_UNAUTHORIZED + " Unauthorized");

    }
}