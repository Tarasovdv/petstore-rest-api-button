package petstore;

import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.buttonone.petstore.api.PetService;
import ru.buttonone.petstore.data.Pet;

public class PetParamTest extends BaseTest {
    private final PetService petService = new PetService();

    @Description("TC-8 Проверка параметров добавленного питомца")
    @DisplayName("Проверка параметров добавленного питомца")
    @ParameterizedTest
    @MethodSource("petstore.TestData#addNewPetTestData")
    public void checkParamNewPet(Pet newPet, long petId) {
        petService
                .addNewPet(newPet, petId)
                .checkPetParam(newPet, petId)
                .cleanPetData(petId);
    }
}