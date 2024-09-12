package tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestDelete extends BaseTest {

    @Test
    public void deleteUser() {

        given(getRequestSpec())
                .delete("users/2")
                .then()
                .assertThat().statusCode(204);
    }
}