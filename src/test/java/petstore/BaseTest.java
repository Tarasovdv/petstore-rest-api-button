package petstore;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }
}