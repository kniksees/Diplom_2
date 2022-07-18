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

public class GetOrderTest {

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
    public void getOrderWithAuthUser() {
        Response response = getOrderListWithAuthToken(responseAboutRegisterWithCorrectUniqueData);

        assertEquals(response.statusCode(), SC_OK);
    }

    @Test
    public void getOrderWithOutAuthUser() {
        Response response = getOrderListWithOutAuthToken();

        assertEquals(response.statusCode(), SC_UNAUTHORIZED);
    }
}