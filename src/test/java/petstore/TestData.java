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
    private static final String PET_JSON = petData();
    private static final String UPDATE_PET_JSON = updatePetData();
    public final static Category CATEGORY = new Category(0L, "cat");
    public final static Category UPDATE_CATEGORY = new Category(1L, "sphinx");
    public final static List<String> PHOTO_URLS = List.of("photo_1", "photo_2", "photo_3");
    public final static List<String> UPDATE_PHOTO_URLS = List.of("photo_11", "photo_22", "photo_33");
    public final static List<Tag> TAGS = List.of(new Tag(0L, "black"), new Tag(1L, "small"));
    public final static List<Tag> UPDATE_TAGS = List.of(new Tag(2L, "sphinx"), new Tag(3L, "canada"));
    private static final String PET_NAME = "KENSEY";
    private static final String UPDATE_PET_NAME = "MIKASA";
    private static final String PET_STATUS = "sold";
    private static final String UPDATE_PET_STATUS = "available";


    protected static Stream<Arguments> addNewPetTestData() {
        return Stream.of(Arguments.of(PET_JSON, PET_ID, CATEGORY, PET_NAME, PHOTO_URLS, TAGS, PET_STATUS));
    }

    protected static Stream<Arguments> findPetByIdTestData() {
        return Stream.of(Arguments.of(PET_JSON));
    }

    protected static Stream<Arguments> updatePartialPetTestData() {
        return Stream.of(Arguments.of(PET_JSON, UPDATE_PET_NAME, UPDATE_PET_STATUS));
    }

    protected static Stream<Arguments> updateFullPetTestData() {
        return Stream.of(Arguments.of(PET_JSON, UPDATE_PET_JSON, PET_ID, UPDATE_PET_STATUS, UPDATE_PET_NAME,
                UPDATE_PHOTO_URLS, UPDATE_TAGS, UPDATE_PET_STATUS));
    }

    protected static Stream<Arguments> deletePetByIdTestData() {
        return Stream.of(Arguments.of(PET_JSON));
    }

    @SneakyThrows
    private static String petData() {
        return objectIntoJson(createPetData(CATEGORY, PET_NAME, PHOTO_URLS, TAGS, PET_STATUS));
    }

    @SneakyThrows
    private static String updatePetData() {
        return objectIntoJson(createPetData(UPDATE_CATEGORY, UPDATE_PET_NAME, UPDATE_PHOTO_URLS, UPDATE_TAGS,
                UPDATE_PET_STATUS));
    }

    private static Pet createPetData(Category category, String petName, List<String> photoUrl,
                                     List<Tag> tags, String status) {
        return new Pet(TestData.PET_ID, category, petName, photoUrl, tags, status);
    }

    private static String objectIntoJson(Object object) throws JsonProcessingException {
        return new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);
    }
}