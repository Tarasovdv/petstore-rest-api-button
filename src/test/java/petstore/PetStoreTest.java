package petstore;

import io.qameta.allure.Description;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.buttonone.petstore.api.Pet;

public class PetStoreTest extends BaseTest {
    private final Pet pet = new Pet();

    @Description("Добавление нового питомца")
    @ParameterizedTest
    @MethodSource("petstore.TestData#addNewPetTestData")
    public void addNewPet(String petJson) {
        DELETE_PET = true;
        pet.addNewPet(petID, petJson)
                .findPetById(petID);
    }

    @Description("Предоставление питомца по id")
    @ParameterizedTest
    @MethodSource("petstore.TestData#findPetByIdTestData")
    public void findPetById(String petJson) {
        DELETE_PET = true;
        pet.addNewPet(petID, petJson)
                .findPetById(petID);
    }

    @Description("Изменение имени и статуса питомца")
    @ParameterizedTest
    @MethodSource("petstore.TestData#updatePartialPetTestData")
    public void updatePartialPet(String petJson, String updatedName, String updatedStatus) {
        DELETE_PET = true;
        pet.addNewPet(petID, petJson)
                .partialUpdatePet(petID, updatedName, updatedStatus)
                .findPetById(petID);
    }

    @Description("Изменение данных о питомце")
    @ParameterizedTest
    @MethodSource("petstore.TestData#updateFullPetTestData")
    public void updateFullPet(String petJson, String updatePetJson) {
        DELETE_PET = true;
        pet.addNewPet(petID, petJson)
                .fullUpdatePet(updatePetJson)
                .findPetById(petID);
    }

    @Description("Удаление питомца по id")
    @ParameterizedTest
    @MethodSource("petstore.TestData#deletePetByIdTestData")
    public void deletePetById(String petJson) {
        pet.addNewPet(petID, petJson)
                .deletePetById(petID)
                .checkNoDataAboutPet(petID);
    }

    @Description("Предоставление всех питомцев по статусу")
    @ParameterizedTest
    @MethodSource("petstore.TestData#findPetByStatusTestData")
    public void findPetsByStatus(String status) {
        pet.findPetByStatus(status);
    }
}