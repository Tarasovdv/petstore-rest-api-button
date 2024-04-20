package petstore;

import io.qameta.allure.Description;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.buttonone.petstore.data.Category;
import ru.buttonone.petstore.data.Tag;

import java.util.List;

import static petstore.TestData.PET_ID;

public class PetServiceTest extends BaseTest {

    @Description("Добавление нового питомца")
    @ParameterizedTest
    @MethodSource("petstore.TestData#addNewPetTestData")
    public void checkParamNewPet(String petJson, long expectId, Category expectCategory, String expectName,
                          List<String> expectPhotoUrls, List<Tag> expectTags, String expectStatus) {
        petService
                .addNewPet(PET_ID, petJson)
                .checkPetParam(PET_ID, expectId, expectCategory, expectName,
                        expectPhotoUrls, expectTags, expectStatus);
    }
}