package petstore;

import org.junit.jupiter.params.provider.Arguments;
import ru.buttonone.petstore.constans.PetStatus;
import ru.buttonone.petstore.data.Category;
import ru.buttonone.petstore.data.Pet;
import ru.buttonone.petstore.data.Tag;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.valueOf;
import static ru.buttonone.petstore.constans.PetStatus.available;
import static ru.buttonone.petstore.constans.PetStatus.sold;
import static ru.buttonone.petstore.constans.TestValue.*;

public class TestData {

    private static Category categoryData(long categoryID, String categoryName) {
        return new Category(categoryID, categoryName);
    }

    private static List<Tag> tagData(long tagId, String tagName) {
        return List.of(new Tag(tagId, tagName));
    }

    private static Pet createPetData(long petId, Category category, String petName, List<String> photoUrl,
                                     List<Tag> tags, String status) {
        return new Pet(petId, category, petName, photoUrl, tags, status);
    }

    public static Stream<Arguments> addNewPetTestData() {
        Pet newPet = createPetData(PET_ID, categoryData(CATEGORY_ID, CATEGORY_NAME),
                PET_NAME, PHOTO_URL_LIST, tagData(TAG_ID, TAG_NAME), valueOf(sold));
        return Stream.of(Arguments.of(newPet, PET_ID));
    }

    public static Stream<Arguments> findPetByIdTestData() {
        Pet newPet = createPetData(PET_ID, categoryData(CATEGORY_ID, CATEGORY_NAME),
                PET_NAME, PHOTO_URL_LIST, tagData(TAG_ID, TAG_NAME), valueOf(sold));
        return Stream.of(Arguments.of(newPet, PET_ID));
    }

    public static Stream<Arguments> updatePartialPetTestData() {
        Pet newPet = createPetData(PET_ID, categoryData(CATEGORY_ID, CATEGORY_NAME),
                PET_NAME, PHOTO_URL_LIST, tagData(TAG_ID, TAG_NAME), valueOf(sold));
        return Stream.of(Arguments.of(newPet, PET_ID, UPDATE_PET_NAME, valueOf(available)));
    }

    public static Stream<Arguments> updateFullPetTestData() {
        Pet newPet = createPetData(PET_ID, categoryData(CATEGORY_ID, CATEGORY_NAME),
                PET_NAME, PHOTO_URL_LIST, tagData(TAG_ID, TAG_NAME), valueOf(sold));
        Pet updatePet = createPetData(PET_ID, categoryData(UPDATE_CATEGORY_ID, UPDATE_CATEGORY_NAME),
                UPDATE_PET_NAME, UPDATE_PHOTO_URL_LIST, tagData(UPDATE_TAG_ID, UPDATE_TAG_NAME), valueOf(available));
        return Stream.of(Arguments.of(newPet, updatePet, PET_ID));
    }

    public static Stream<Arguments> deletePetByIdTestData() {
        Pet newPet = createPetData(PET_ID, categoryData(CATEGORY_ID, CATEGORY_NAME),
                PET_NAME, PHOTO_URL_LIST, tagData(TAG_ID, TAG_NAME), valueOf(sold));
        return Stream.of(Arguments.of(newPet, PET_ID));
    }
}