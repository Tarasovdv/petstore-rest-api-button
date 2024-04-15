package petstore;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.buttonone.petstore.api.Pet;

public class BaseTest extends TestData{
    static boolean DELETE_PET;
    private final Pet pet = new Pet();

    @BeforeEach
    public void setFilter(){
        RestAssured.filters(new AllureRestAssured());
    }

    @AfterEach
    public void clearPetTestData() {
        if (DELETE_PET) {
            pet.deletePetById(petID);
            DELETE_PET = false;
        }
    }
}