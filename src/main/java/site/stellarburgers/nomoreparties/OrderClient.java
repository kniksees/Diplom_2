package site.stellarburgers.nomoreparties;

import io.restassured.response.Response;
import site.stellarburgers.nomoreparties.model.Order;
import site.stellarburgers.nomoreparties.model.User;
import site.stellarburgers.nomoreparties.model.UserCredentials;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseApi {

    public static Response makeOrderWithAuthorizationUser(Response response, Order order) {

        String accessToken = response.body().jsonPath().getString("accessToken");
        return given()
                .spec(getRequestSpecification())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(BASE_URL + "/api/orders");
    }

    public static Response makeOrderWithOutAuthorizationUser(Order order) {

        return given()
                .spec(getRequestSpecification())
                .body(order)
                .when()
                .post(BASE_URL + "/api/orders");
    }


}
