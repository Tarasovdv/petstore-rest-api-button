package petstore;

import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.buttonone.petstore.api.PetService;

import static io.restassured.RestAssured.given;
import static petstore.TestData.PET_ID;
import static ru.buttonone.petstore.api.Endpoint.PET_BY_ID;
import static ru.buttonone.petstore.spec.Spec.requestSpec;
import static ru.buttonone.petstore.spec.Spec.responseSpec;

@Slf4j
public class BaseTest {
    public final PetService petService = new PetService();

    @BeforeEach
    public void setFilter() {
        RestAssured.filters(new AllureRestAssured());
    }

    @AfterEach
    @Description("Очистка данных после тестирование")
    @ParameterizedTest
    @MethodSource("petstore.TestData#deletePetByIdTestData")
    public void cleanData() {
        cleanPetData(PET_ID);
    }

    private void cleanPetData(long petId) {
        log.info(String.format("CLEAN_PET_DATA by ID = {%s}", petId));

        if (petService.checkPetExistById(petId)) {
            log.info(String.format("PET with ID = {%s} in DB", petId));
            given()
                    .spec(requestSpec())
                    .pathParam("petId", petId)
                    .when()
                    .delete(PET_BY_ID)
                    .then()
                    .spec(responseSpec());
            log.info(String.format("CLEAN_PET_DATA by ID = {%s} -> SUCCESS", petId));
        }
        log.info(String.format("PET with ID = {%s} not found in DB", petId));
    }
}