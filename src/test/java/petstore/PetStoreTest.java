package petstore;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.buttonone.petstore.api.PetService;
import ru.buttonone.petstore.constans.PetStatus;
import ru.buttonone.petstore.data.Pet;

@Isolated
public class PetStoreTest {
    private final PetService petService = new PetService();

    @DisplayName("Добавление нового питомца")
    @ParameterizedTest
    @MethodSource("petstore.TestData#addNewPetTestData")
    public void addNewPet(Pet newPet, long petId) {
        petService.addNewPet(newPet, petId)
                .findPetById(petId)
                .cleanPetData(petId);
    }

    @DisplayName("Предоставление питомца по id")
    @ParameterizedTest
    @MethodSource("petstore.TestData#findPetByIdTestData")
    public void findPetById(Pet newPet, long petId) {
        petService.addNewPet(newPet, petId)
                .findPetById(petId)
                .cleanPetData(petId);
    }

    @DisplayName("Изменение имени и статуса питомца")
    @ParameterizedTest
    @MethodSource("petstore.TestData#updatePartialPetTestData")
    public void updatePartialPet(Pet newPet, long petId, String updatedName, String updatedStatus) {
        petService.addNewPet(newPet, petId)
                .partialUpdatePet(petId, updatedName, updatedStatus)
                .findPetById(petId)
                .cleanPetData(petId);
    }

    @DisplayName("Изменение данных о питомце")
    @ParameterizedTest
    @MethodSource("petstore.TestData#updateFullPetTestData")
    public void updateFullPet(Pet newPet, Pet updatePet, long petId) {
        petService.addNewPet(newPet, petId)
                .fullUpdatePet(updatePet)
                .findPetById(petId)
                .cleanPetData(petId);
    }

    @DisplayName("Удаление питомца по id")
    @ParameterizedTest
    @MethodSource("petstore.TestData#deletePetByIdTestData")
    public void deletePetById(Pet newPet, long petId) {
        petService
                .addNewPet(newPet, petId)
                .deletePetById(petId);
    }

    @DisplayName("Проверка поиска питомцев по статусу")
    @ParameterizedTest
    @EnumSource (PetStatus.class)
    public void findPetsByStatus(PetStatus status) {
        petService.findPetByStatus(status);
    }
}