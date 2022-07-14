package site.stellarburgers.nomoreparties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseApiUser {
    public static String BASE_URL = "https://stellarburgers.nomoreparties.site";

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder().log(LogDetail.ALL)
                .setContentType(ContentType.JSON).build();
    }
}
