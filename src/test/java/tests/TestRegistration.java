package tests;

import org.testng.annotations.Test;

public class TestRegistration extends BaseTest {

    @Test
    public void registrationPositive() {

        user.setPassword("pokpok");
        register(user).then().statusCode(200);
    }

    @Test
    public void registrationNegative_wrongPassword() {

        user.setPassword("");
        register(user).then().statusCode(400);
    }
}