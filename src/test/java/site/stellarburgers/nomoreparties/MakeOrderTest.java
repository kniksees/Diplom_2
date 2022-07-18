package site.stellarburgers.nomoreparties;

import io.restassured.response.Response;
import org.apache.xpath.operations.Or;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.stellarburgers.nomoreparties.model.Order;
import site.stellarburgers.nomoreparties.model.User;
import site.stellarburgers.nomoreparties.model.UserCredentials;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static site.stellarburgers.nomoreparties.OrderClient.makeOrderWithAuthorizationUser;
import static site.stellarburgers.nomoreparties.OrderClient.makeOrderWithOutAuthorizationUser;
import static site.stellarburgers.nomoreparties.UserClient.createUser;
import static site.stellarburgers.nomoreparties.UserClient.deleteUser;

public class MakeOrderTest {

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
    public void makeOrderWithAuthorizationUserTest() {
        Order order = Order.getOrder();
        Response response = makeOrderWithAuthorizationUser(responseAboutRegisterWithCorrectUniqueData, order);
        assertEquals(response.statusCode(), SC_OK);
    }

    @Test
    public void makeOrderWithOutAuthorizationUserTest() {
        Order order = Order.getOrder();
        Response response = makeOrderWithOutAuthorizationUser(order);
        assertEquals(response.statusCode(), SC_UNAUTHORIZED);
    }

    @Test
    public void makeOrderWithAuthorizationUserWithBadHashTest() {
        Order order = Order.getOrderWithBadHash();
        Response response = makeOrderWithAuthorizationUser(responseAboutRegisterWithCorrectUniqueData, order);
        assertEquals(response.statusCode(), SC_BAD_REQUEST);
    }

    @Test
    public void makeOrderWithAuthorizationUserWithOutIngredientsTest() {
        Order order = Order.getOrderWithOutIngredients();
        Response response = makeOrderWithAuthorizationUser(responseAboutRegisterWithCorrectUniqueData, order);
        assertEquals(response.statusCode(), SC_BAD_REQUEST);
    }
}
