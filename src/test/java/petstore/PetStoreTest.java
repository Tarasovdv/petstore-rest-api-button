package petstore;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.buttonone.petstore.api.PetService;
import ru.buttonone.petstore.constans.PetStatus;
import ru.buttonone.petstore.data.Pet;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.lang.String.valueOf;
import static ru.buttonone.petstore.api.PetService.REQUEST_SPEC;
import static ru.buttonone.petstore.api.PetService.RESPONSE_SPEC;
import static ru.buttonone.petstore.constans.Endpoint.PET;

@Slf4j
@Isolated
public class PetStoreTest extends BaseTest {
    private final PetService petService = new PetService();

    @DisplayName("Добавление нового питомца")
    @Description("TC-1 Отправка запроса на добавление нового питомца")
    @ParameterizedTest
    @MethodSource("petstore.TestData#addNewPetTestData")
    public void addNewPet(Pet newPet, long petId) {
        log.info("> START");
        petService.addNewPet(newPet, petId)
                .findPetById(petId)
                .cleanPetData(petId);
        log.info("> FINISH");
    }

    @Description("TC-2 Отправка запроса на предоставление информации о питомце")
    @DisplayName("Предоставление питомца по id")
    @ParameterizedTest
    @MethodSource("petstore.TestData#findPetByIdTestData")
    public void findPetById(Pet newPet, long petId) {
        log.info("> START");
        petService.addNewPet(newPet, petId)
                .findPetById(petId)
                .cleanPetData(petId);
        log.info("> FINISH");
    }

    @Description("TC-3 Отправка запроса на частичное изменение данных о питомце")
    @DisplayName("Изменение имени и статуса питомца")
    @ParameterizedTest
    @MethodSource("petstore.TestData#updatePartialPetTestData")
    public void updatePartialPet(Pet newPet, long petId, String updatedName, String updatedStatus) {
        log.info("> START");
        petService.addNewPet(newPet, petId)
                .partialUpdatePet(petId, updatedName, updatedStatus)
                .findPetById(petId)
                .cleanPetData(petId);
        log.info("> FINISH");
    }

    @Description("TC-4 Отправка запроса на полное изменение данных о питомце")
    @DisplayName("Изменение данных о питомце")
    @ParameterizedTest
    @MethodSource("petstore.TestData#updateFullPetTestData")
    public void updateFullPet(Pet newPet, Pet updatePet, long petId) {
        log.info("> START");
        petService.addNewPet(newPet, petId)
                .fullUpdatePet(updatePet)
                .findPetById(petId)
                .cleanPetData(petId);
        log.info("> FINISH");
    }

    @Description("TC-5 Отправка запроса на удаление питомца")
    @DisplayName("Удаление питомца по id")
    @ParameterizedTest
    @MethodSource("petstore.TestData#deletePetByIdTestData")
    public void deletePetById(Pet newPet, long petId) {
        log.info("> START");
        petService
                .addNewPet(newPet, petId)
                .deletePetById(petId);
        log.info("> FINISH");
    }

    @Description("TC-6 Отправка запроса на предоставление питомцев по статусу")
    @DisplayName("Проверка поиска питомцев по статусу")
    @ParameterizedTest
    @EnumSource(PetStatus.class)
    public void findPetsByStatus(PetStatus status) {
        log.info("> START");
        petService.findPetByStatus(valueOf(status).toLowerCase());
        log.info("> FINISH");
    }

    @Description("TC-7 Проверка структуры данных о питомце")
    @DisplayName("Проверка структуры данных о питомце")
    @ParameterizedTest
    @MethodSource("petstore.TestData#addNewPetTestData")
    public void checkPetSchema(Pet newPet, long petId) {
        log.info("> START");
        log.info("Добавление нового питомца с ID = " + petId);
        if (petService.checkPetExistById(petId)) {
            log.error("Питомец уже существует -> ID = " + petId);
            throw new RuntimeException("Питомец с ID = " + petId + " уже существует");
        }

        given()
                .spec(REQUEST_SPEC)
                .contentType(JSON)
                .body(newPet)
                .when()
                .post(PET)
                .then()
                .spec(RESPONSE_SPEC)
                .contentType(ContentType.JSON)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("pet_schema.json"));

        petService.cleanPetData(petId);
        log.info("> FINISH");
    }
}