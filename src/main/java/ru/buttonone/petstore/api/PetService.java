package ru.buttonone.petstore.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import ru.buttonone.petstore.constans.PetStatus;
import ru.buttonone.petstore.data.Pet;
import ru.buttonone.petstore.spec.Spec;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.buttonone.petstore.constans.Endpoint.*;
import static ru.buttonone.petstore.spec.Spec.requestSpec;
import static ru.buttonone.petstore.spec.Spec.responseSpec;


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

    public void findPetByStatus(String status) {
        log.info(String.format("Предоставление всех питомцев со статусом = {%s}", status));
        given()
                .spec(REQUEST_SPEC)
                .queryParam("status", status)
                .when()
                .get(PET_BY_STATUS)
                .then()
                .spec(RESPONSE_SPEC);
    }

    public PetService addNewPet(Pet newPet, long petId) {
        log.info(String.format("Добавление нового питомца с ID = {%s}", petId));
        if (checkPetExistById(petId)) {
            log.error(String.format("Питомец уже существует -> ID = {%s}", petId));
            throw new RuntimeException("Питомец с ID = " + petId + " уже существует");
        }

        given()
                .spec(REQUEST_SPEC)
                .contentType(JSON)
                .body(newPet)
                .when()
                .post(PET)
                .then()
                .spec(RESPONSE_SPEC);

        return this;
    }

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

    public PetService fullUpdatePet(Pet updatePet) {
        log.info("Полное изменение данных о питомце");
        given()
                .spec(REQUEST_SPEC)
                .contentType(JSON)
                .body(updatePet)
                .when()
                .put(PET)
                .then()
                .spec(RESPONSE_SPEC);

        return this;
    }

    public PetService checkPetParam(Pet checkPet, long petId) {
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
                () -> assertEquals(checkPet.getId(), response.getId(),
                        "Actual PET ID = " + response.getId()
                                + "\nExpect PET ID = " + checkPet.getId()),
                () -> assertEquals(checkPet.getCategory(), response.getCategory(),
                        "Actual PET CATEGORY = " + response.getCategory() +
                                "\nExpect PET CATEGORY = " + checkPet.getCategory()),
                () -> assertEquals(checkPet.getName(), response.getName(),
                        "Actual PET NAME = " + response.getName() +
                                "\nExpect PET NAME = " + checkPet.getName()),
                () -> assertEquals(checkPet.getPhotoUrls(), response.getPhotoUrls(),
                        "Actual PET PHOTO URL = " + response.getPhotoUrls() +
                                "\nExpect PET PHOTO URL = " + checkPet.getPhotoUrls()),
                () -> assertEquals(checkPet.getTags(), response.getTags(),
                        "Actual PET TAG = " + response.getTags() +
                                "\nExpect PET TAG = " + checkPet.getTags()),
                () -> assertEquals(checkPet.getStatus(), response.getStatus(),
                        "Actual PET STATUS = " + response.getStatus() +
                                "\nExpect PET STATUS = " + checkPet.getStatus()));

        return this;
    }

    public void cleanPetData(long petId) {
        log.info(String.format("CLEAN_PET_DATA by ID = {%s}", petId));

        if (checkPetExistById(petId)) {
            log.info(String.format("PET with ID = {%s} in DB", petId));
            given()
                    .spec(requestSpec())
                    .pathParam("petId", petId)
                    .when()
                    .delete(PET_BY_ID)
                    .then()
                    .spec(responseSpec());
            log.info(String.format("CLEAN_PET_DATA by ID = {%s} -> SUCCESS", petId));
        }
        log.info(String.format("PET with ID = {%s} not found in DB", petId));
    }
}