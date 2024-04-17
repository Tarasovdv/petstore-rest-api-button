package petstore;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.buttonone.petstore.api.PetService;

import static petstore.TestData.PET_ID;

public class PetStoreTest extends BaseTest {
    private final PetService petService = new PetService();

    @Description("Добавление нового питомца")
    @ParameterizedTest
    @MethodSource("petstore.TestData#addNewPetTestData")
    public void addNewPet(String petJson) {
        petService.addNewPet(PET_ID, petJson)
                .findPetById(PET_ID);
    }

    @Description("Предоставление питомца по id")
    @ParameterizedTest
    @MethodSource("petstore.TestData#findPetByIdTestData")
    public void findPetById(String petJson) {
        petService.addNewPet(PET_ID, petJson)
                .findPetById(PET_ID);
    }

    @Description("Изменение имени и статуса питомца")
    @ParameterizedTest
    @MethodSource("petstore.TestData#updatePartialPetTestData")
    public void updatePartialPet(String petJson, String updatedName, String updatedStatus) {
        petService.addNewPet(PET_ID, petJson)
                .partialUpdatePet(PET_ID, updatedName, updatedStatus)
                .findPetById(PET_ID);
    }

    @Description("Изменение данных о питомце")
    @ParameterizedTest
    @MethodSource("petstore.TestData#updateFullPetTestData")
    public void updateFullPet(String petJson, String updatePetJson) {
        petService.addNewPet(PET_ID, petJson)
                .fullUpdatePet(updatePetJson)
                .findPetById(PET_ID);
    }

    @Description("Удаление питомца по id")
    @ParameterizedTest
    @MethodSource("petstore.TestData#deletePetByIdTestData")
    public void deletePetById(String petJson) {
        petService.addNewPet(PET_ID, petJson)
                .deletePetById(PET_ID);
    }

    @ParameterizedTest
    @ValueSource(strings = {"available", "pending", "sold"})
    @DisplayName("Проверка поиска питомцев по статусу")
    public void findPetsByStatus(String status) {
        petService.findPetByStatus(status);
    }
}