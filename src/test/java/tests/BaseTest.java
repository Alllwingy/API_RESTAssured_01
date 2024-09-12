package tests;

import dto.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected static RequestSpecification getRequestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .build();
    }

    protected static ResponseSpecification getResponseSpec() {

        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }

    static User user;

    @BeforeSuite
    public void setUp() {

        user = User.builder()
                .email("eve.holt@reqres.in")
                .build();
    }

    protected Response register(User user) {

        return given(getRequestSpec())
                .body(user)
                .post("register")
                .then()
                .spec(getResponseSpec())
                .extract().response();
    }
}