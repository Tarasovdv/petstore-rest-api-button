package petstore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.provider.Arguments;
import ru.buttonone.petstore.data.Category;
import ru.buttonone.petstore.data.Pet;
import ru.buttonone.petstore.data.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestData {
    static int petID = 710710710;
    static String petJson;
    static String updatePetJson;

    static Stream<Arguments> addNewPetTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(petJson));
    }

    static Stream<Arguments> findPetByIdTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(petJson));
    }

    static Stream<Arguments> updatePartialPetTestData() throws JsonProcessingException {
        String updatedName = "Kensey";
        String updatedStatus = "sold";
        return Stream.of(Arguments.of(petJson, updatedName, updatedStatus));
    }

    static Stream<Arguments> updateFullPetTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(petJson, updatePetJson));
    }

    static Stream<Arguments> deletePetByIdTestData() throws JsonProcessingException {
        return Stream.of(Arguments.of(petJson));
    }

    static Stream<Arguments> findPetByStatusTestData() {
        String status = "sold";
        return Stream.of(Arguments.of(status));
    }

    private static String petData(int petId) throws JsonProcessingException {
        List<String> photoUrls = new ArrayList<>(List.of("photoUrl"));
        List<Tag> tags = List.of(new Tag(0, "tag"));
        Category category = new Category(0, "category_name");
        Pet pet = new Pet(petId, category, "Mikasa", photoUrls, tags, "available");
        return objectIntoJson(pet);
    }

    private static String updatePetData(int petId) throws JsonProcessingException {
        List<String> photoUrls = new ArrayList<>(List.of("photoUrl"));
        List<Tag> tags = List.of(new Tag(0, "tags"));
        Category category = new Category(0, "category_name");
        Pet pet = new Pet(petId, category, "Luna", photoUrls, tags, "sold");
        return objectIntoJson(pet);
    }

    private static String objectIntoJson(Object object) throws JsonProcessingException {
        return new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);
    }

    static {
        try {
            petJson = petData(petID);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            updatePetJson = updatePetData(petID);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}