package site.stellarburgers.nomoreparties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import site.stellarburgers.nomoreparties.model.User;
import site.stellarburgers.nomoreparties.model.UserCredentials;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;

public class UserClient extends BaseApiUser {



    public static Response createUser(User user) {

        return given()
                .spec(getRequestSpecification())
                .body(user)
                .when()
                .post(BASE_URL + "/api/auth/register/");
    }

    public static Response deleteUser(Response response) {

        String accessToken = response.body().jsonPath().getString("accessToken");
        return given()
                .spec(getRequestSpecification())
                .header("Authorization", accessToken)
                .when()
                .delete(BASE_URL+ "/api/auth/user/");


    }

    public static Response loginUserWithCorrectData(UserCredentials userCredentials) {
        return given()
                .spec(getRequestSpecification())
                .body(userCredentials)
                .when()
                .post(BASE_URL + "/api/auth/login/");
    }

    public static Response changeUserDataWithAuthorization(Response response, User user) {
        String accessToken = response.body().jsonPath().getString("accessToken");
        return given()
                .spec(getRequestSpecification())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(BASE_URL + "/api/auth/user/");
    }

    public static Response changeUserDataWithOutAuthorization(User user) {
        return given()
                .spec(getRequestSpecification())
                .body(user)
                .when()
                .patch(BASE_URL + "/api/auth/user/");
    }





}
