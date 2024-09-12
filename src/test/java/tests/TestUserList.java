package tests;

import dto.Data;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class TestUserList extends BaseTest {

    /**
     * Fetch entire list of users from all pages
     * Assert that all users' emails end with '@reqres.in'
     */

    @Test
    public void getUserList() {

        List<Data> alllUsers = new ArrayList<>();
        int page = 1;
        boolean hasMorePages = true;

        while (hasMorePages) {

            Response response = given(getRequestSpec())
                    .queryParam("page", page)
                    .get("users")
                    .then()
                    .spec(getResponseSpec())
                    .statusCode(200)
                    .extract().response();

            List<Data> users = response.jsonPath().getList("data", Data.class);
            alllUsers.addAll(users);

            int totalPages = response.jsonPath().getInt("total_pages");

            if (page >= totalPages) {
                hasMorePages = false;
            } else {
                page++;
            }
        }

//        boolean matches = true;
//
//        for (dto_Data user : alllUsers) {
//
//            if (!Pattern.matches(".*@reqres\\.in$", user.getEmail())) { // if (!user.getEmail().endsWith("@reqres.in")) {}
//                matches = false;
//                break;
//            }
//        }
//
//        if (matches) {
//            System.out.println("All users' emails end with '@reqres.in'");
//        } else {
//            System.out.println("Not all users' emails end with '@reqres.in'");
//        }

        for (Data user : alllUsers) {

            String email = user.getEmail();
            assert Pattern.compile(".*@reqres\\.in$").matcher(email).matches() : "Not all users' emails end with '@reqres.in': " + email;
        }

        System.out.println("All users' emails end with '@reqres.in'");
    }
}