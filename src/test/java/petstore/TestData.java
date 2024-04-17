package petstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.params.provider.Arguments;
import ru.buttonone.petstore.data.Category;
import ru.buttonone.petstore.data.Pet;
import ru.buttonone.petstore.data.Tag;

import java.util.List;
import java.util.stream.Stream;

public class TestData {
    public static final long PET_ID = 710710710;
    private static final String PET_JSON = petData(PET_ID);
    private static final String UPDATE_PET_JSON = updatePetData(PET_ID);

    protected static Stream<Arguments> addNewPetTestData() {
        return Stream.of(Arguments.of(PET_JSON));
    }

    protected static Stream<Arguments> findPetByIdTestData() {
        return Stream.of(Arguments.of(PET_JSON));
    }

    protected static Stream<Arguments> updatePartialPetTestData() {
        return Stream.of(Arguments.of(PET_JSON, "Kensey", "sold"));
    }

    protected static Stream<Arguments> updateFullPetTestData() {
        return Stream.of(Arguments.of(PET_JSON, UPDATE_PET_JSON));
    }

    protected static Stream<Arguments> deletePetByIdTestData() {
        return Stream.of(Arguments.of(PET_JSON));
    }

    @SneakyThrows
    private static String petData(long petId) {
        return objectIntoJson(createPetData(petId, 0, "category_name", "Mikasa"
                , "photoUrl", 0, "tags", "available"));
    }

    @SneakyThrows
    private static String updatePetData(long petId) {
        return objectIntoJson(createPetData(petId, 0, "category_name", "Luna"
                , "photoUrl", 0, "tags", "sold"));
    }

    private static Pet createPetData(long petId, long categoryId, String categoryName, String petName, String photoUrl
            , long tagId, String tagName, String status) {
        return new Pet(petId, new Category(categoryId, categoryName), petName, List.of(photoUrl)
                , List.of(new Tag(tagId, tagName)), status);
    }

    private static String objectIntoJson(Object object) throws JsonProcessingException {
        return new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);
    }
}