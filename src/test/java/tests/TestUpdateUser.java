package tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("User Management")
@Feature("User Update")
public class TestUpdateUser extends BaseTest {

    @Test
    @Story("Update User Details")
    @Description("Verify that user details can be updated successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void updateUser_TestNG() {

        String requestBody = """
                {
                    "name": "morpheus",
                    "job": "zion resident"
                }
                """;

        Response response = updateUserRequest(requestBody);

        validateResponse(response);
        validateUpdatedTimestamp(response);
    }

    @Step("Send update user request")
    private Response updateUserRequest(String requestBody) {

        return given(getRequestSpec())
                .body(requestBody)
                .when()
                .patch("users/2")
                .then()
                .spec(getResponseSpec())
                .extract().response();
    }

    @Step("Validate response")
    private void validateResponse(Response response) {

        response.then()
                .statusCode(200)
                .contentType("application/json")
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"));

        Allure.addAttachment("Response Body", response.getBody().asString());
    }

    @Step("Validate updated timestamp")
    private void validateUpdatedTimestamp(Response response) {

        String updatedAtStr = response.jsonPath().getString("updatedAt");
        OffsetDateTime updatedAt = OffsetDateTime.parse(updatedAtStr);

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.of("+02:00"));
        Assert.assertTrue(ChronoUnit.SECONDS.between(updatedAt, now) <= 2, "Updated timestamp should be within 1 second of current time");
    }
}