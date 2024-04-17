package petstore;

import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.buttonone.petstore.api.PetService;

import static petstore.TestData.PET_ID;

public class BaseTest {
    private final PetService petService = new PetService();

    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }

    @AfterEach
    @Description("Очистка данных после тестирование")
    @ParameterizedTest
    @MethodSource("petstore.TestData#deletePetByIdTestData")
    public void cleanData() {
        petService.deletePetById(PET_ID);
    }
}