package petstore;

import io.qameta.allure.Description;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.buttonone.petstore.api.PetService;

public class PetStoreTest extends BaseTest {
    private final PetService petService = new PetService();

    @Description("Добавление нового питомца")
    @ParameterizedTest
    @MethodSource("petstore.TestData#addNewPetTestData")
    public void addNewPet(String petJson) {
        DELETE_PET = true;
        petService.addNewPet(petId, petJson)
                .findPetById(petId);
    }

    @Description("Предоставление питомца по id")
    @ParameterizedTest
    @MethodSource("petstore.TestData#findPetByIdTestData")
    public void findPetById(String petJson) {
        DELETE_PET = true;
        petService.addNewPet(petId, petJson)
                .findPetById(petId);
    }

    @Description("Изменение имени и статуса питомца")
    @ParameterizedTest
    @MethodSource("petstore.TestData#updatePartialPetTestData")
    public void updatePartialPet(String petJson, String updatedName, String updatedStatus) {
        DELETE_PET = true;
        petService.addNewPet(petId, petJson)
                .partialUpdatePet(petId, updatedName, updatedStatus)
                .findPetById(petId);
    }

    @Description("Изменение данных о питомце")
    @ParameterizedTest
    @MethodSource("petstore.TestData#updateFullPetTestData")
    public void updateFullPet(String petJson, String updatePetJson) {
        DELETE_PET = true;
        petService.addNewPet(petId, petJson)
                .fullUpdatePet(updatePetJson)
                .findPetById(petId);
    }

    @Description("Удаление питомца по id")
    @ParameterizedTest
    @MethodSource("petstore.TestData#deletePetByIdTestData")
    public void deletePetById(String petJson) {
        petService.addNewPet(petId, petJson)
                .deletePetById(petId)
                .checkNoDataAboutPet(petId);
    }

    @Description("Предоставление всех питомцев по статусу")
    @ParameterizedTest
    @MethodSource("petstore.TestData#findPetByStatusTestData")
    public void findPetsByStatus(String status) {
        petService.findPetByStatus(status);
    }
}