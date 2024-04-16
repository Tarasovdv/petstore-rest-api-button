package petstore;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.buttonone.petstore.api.PetService;

public class BaseTest extends TestData{
    static boolean DELETE_PET;
    private final PetService petService = new PetService();

    @BeforeEach
    public void setFilter(){
        RestAssured.filters(new AllureRestAssured());
    }

    @AfterEach
    public void clearPetTestData() {
        if (DELETE_PET) {
            petService.deletePetById(petId);
            DELETE_PET = false;
        }
    }
}