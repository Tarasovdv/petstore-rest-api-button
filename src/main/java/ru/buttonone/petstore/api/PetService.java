package ru.buttonone.petstore.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;
import lombok.extern.slf4j.Slf4j;
import ru.buttonone.petstore.data.Category;
import ru.buttonone.petstore.data.Pet;
import ru.buttonone.petstore.data.Tag;
import ru.buttonone.petstore.spec.Spec;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.buttonone.petstore.api.Endpoint.*;


@Slf4j
public class PetService {
    private static final RequestSpecification REQUEST_SPEC = Spec.requestSpec();
    private static final ResponseSpecification RESPONSE_SPEC = Spec.responseSpec();

    public boolean checkPetExistById(long petId) {
        log.info(String.format("Проверка существование питомца по ID = {%s}", petId));
        Response response = given()
                .spec(REQUEST_SPEC)
                .pathParam("petId", petId)
                .when()
                .get(PET_BY_ID);
        response
                .then()
                .log()
                .status();

        return response.getStatusCode() == 200;
    }

    @Step("Предоставление питомца по id = {id}")
    public PetService findPetById(long petId) {
        log.info(String.format("Предоставление питомца по ID = {%s}", petId));
        given()
                .spec(REQUEST_SPEC)
                .pathParam("petId", petId)
                .when()
                .get(PET_BY_ID)
                .then()
                .spec(RESPONSE_SPEC);

        return this;
    }

    @Step("Удаление питомца по id = {petId}")
    public void deletePetById(long petId) {
        log.info(String.format("Удаление питомца по ID = {%s}", petId));
        if (checkPetExistById(petId)) {
            given()
                    .spec(REQUEST_SPEC)
                    .pathParam("petId", petId)
                    .when()
                    .delete(PET_BY_ID)
                    .then()
                    .spec(RESPONSE_SPEC);

        } else {
            log.error(String.format("Питомца НЕ существует -> ID = {%s}", petId));
            throw new RuntimeException("Питомца с ID = " + petId + " НЕ существует");
        }
    }

    @Step("Предоставление всех питомцев со статусом = {status}")
    public PetService findPetByStatus(String status) {
        log.info(String.format("Предоставление всех питомцев со статусом = {%s}", status));
        given()
                .spec(REQUEST_SPEC)
                .queryParam("status", status)
                .when()
                .get(PET_BY_STATUS)
                .then()
                .spec(RESPONSE_SPEC);

        return this;
    }

    @Step("Добавление нового питомца")
    public PetService addNewPet(long petId, String petJson) {
        log.info(String.format("Добавление нового питомца с ID = {%s}", petId));
        if (checkPetExistById(petId)) {
            log.error(String.format("Питомец уже существует -> ID = {%s}", petId));
            throw new RuntimeException("Питомец с ID = " + petId + " уже существует");
        }

        given()
                .spec(REQUEST_SPEC)
                .contentType(JSON)
                .body(petJson)
                .when()
                .post(PET)
                .then()
                .spec(RESPONSE_SPEC);

        return this;
    }

    @Step("Изменение имени на {name} и статуса питомца на {status} через id = {petId}")
    public PetService partialUpdatePet(long petId, String name, String status) {
        log.info(String.format("Изменение имени на {%s} и статуса питомца на {%s} через ID = {%s}", name, status, petId));
        given()
                .spec(REQUEST_SPEC)
                .pathParam("petId", petId)
                .queryParam("name", name)
                .queryParam("status", status)
                .when()
                .post(PET_BY_ID)
                .then()
                .spec(RESPONSE_SPEC);

        return this;
    }

    @Step("Полное изменение данных о питомце")
    public PetService fullUpdatePet(String petJson) {
        log.info("Полное изменение данных о питомце");
        given()
                .spec(REQUEST_SPEC)
                .contentType(JSON)
                .body(petJson)
                .when()
                .put(PET)
                .then()
                .spec(RESPONSE_SPEC);

        return this;
    }

    @Step("Проверка актуальных полей с ожидаемым результатом")
    public PetService checkPetParam(long petId, long expectId, Category expectCategory, String expectName,
                                    List<String> expectPhotoUrls, List<Tag> expectTags, String expectStatus) {
        log.info(String.format("Предоставление питомца по ID = {%s}", petId));
        Pet response =
                given()
                        .spec(REQUEST_SPEC)
                        .pathParam("petId", petId)
                        .when()
                        .get(PET_BY_ID)
                        .then()
                        .spec(RESPONSE_SPEC)
                        .extract().body().as(Pet.class);

        assertAll(
                () -> assertEquals(expectId, response.getId(),
                        "Actual PET ID = " + response.getId()
                                + "\nExpect PET ID = " + expectId),
                () -> assertEquals(expectCategory, response.getCategory(),
                        "Actual PET CATEGORY = " + response.getCategory() +
                                "\nExpect PET CATEGORY = " + expectCategory),
                () -> assertEquals(expectName, response.getName(),
                        "Actual PET NAME = " + response.getName() +
                                "\nExpect PET NAME = " + expectName),
                () -> assertEquals(expectPhotoUrls, response.getPhotoUrls(),
                        "Actual PET PHOTO URL = " + response.getPhotoUrls() +
                                "\nExpect PET PHOTO URL = " + expectPhotoUrls),
                () -> assertEquals(expectTags, response.getTags(),
                        "Actual PET TAG = " + response.getTags() +
                                "\nExpect PET TAG = " + expectTags),
                () -> assertEquals(expectStatus, response.getStatus(),
                        "Actual PET STATUS = " + response.getStatus() +
                                "\nExpect PET STATUS = " + expectStatus));

        return this;
    }
}