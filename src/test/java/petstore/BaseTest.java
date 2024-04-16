package petstore;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.buttonone.petstore.api.PetService;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static ru.buttonone.petstore.api.PetService.PET_ID;
import static ru.buttonone.petstore.spec.Spec.requestSpec;
import static ru.buttonone.petstore.spec.Spec.responseSpec;

public class BaseTest extends TestData{
    private final PetService petService = new PetService();

    @BeforeEach
    public void setFilter(){
        RestAssured.filters(new AllureRestAssured());
    }

    @AfterEach
    @Description("Очистка данных после тестирование")
    @ParameterizedTest
    @MethodSource("petstore.TestData#deletePetByIdTestData")
    public void cleanData() {
        petService.deletePetById(petId);
    }
}