package ru.buttonone.petstore.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static ru.buttonone.petstore.api.Endpoint.*;
import static ru.buttonone.petstore.spec.Spec.*;

@Log4j2
public class PetService {

    public boolean checkPetExistById(long petId) {
        log.info("Проверка существование питомца по ID = {}", petId);
        Response response = given()
                .spec(requestSpec())
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
        log.info("Предоставление питомца по ID = {}", petId);
        given()
                .spec(requestSpec())
                .pathParam("petId", petId)
                .when()
                .get(PET_BY_ID)
                .then()
                .spec(responseSpec());

        return this;
    }

    @Step("Удаление питомца по id = {petId}")
    public void deletePetById(long petId) {
        log.info("Удаление питомца по ID = {}", petId);
        if (checkPetExistById(petId)) {
            given()
                    .spec(requestSpec())
                    .pathParam("petId", petId)
                    .when()
                    .delete(PET_BY_ID)
                    .then()
                    .spec(responseSpec());

        } else {
            log.error("Питомца НЕ существует -> ID = {}", petId);
            throw new RuntimeException("Питомца с ID = " + petId + " НЕ существует");
        }
    }

    @Step("Предоставление всех питомцев со статусом = {status}")
    public PetService findPetByStatus(String status) {
        log.info("Предоставление всех питомцев со статусом = {}", status);
        given()
                .spec(requestSpec())
                .queryParam("status", status)
                .when()
                .get(PET_BY_STATUS)
                .then()
                .spec(responseSpec());

        return this;
    }

    @Step("Добавление нового питомца")
    public PetService addNewPet(long petId, String petJson) {
        log.info("Добавление нового питомца с ID = {}", petId);
        if (checkPetExistById(petId)) {
            log.error("Питомец уже существует -> ID = {}", petId);
            throw new RuntimeException("Питомец с ID = " + petId + " уже существует");
        }

        given()
                .spec(requestSpec())
                .contentType(JSON)
                .body(petJson)
                .when()
                .post(PET)
                .then()
                .spec(responseSpec());

        return this;
    }

    @Step("Изменение имени на {name} и статуса питомца на {status} через id = {petId}")
    public PetService partialUpdatePet(long petId, String name, String status) {
        log.info("Изменение имени на {} и статуса питомца на {} через ID = {}", name, status, petId);
        given()
                .spec(requestSpec())
                .pathParam("petId", petId)
                .queryParam("name", name)
                .queryParam("status", status)
                .when()
                .post(PET_BY_ID)
                .then()
                .spec(responseSpec());

        return this;
    }

    @Step("Полное изменение данных о питомце")
    public PetService fullUpdatePet(String petJson) {
        log.info("Полное изменение данных о питомце");
        given()
                .spec(requestSpec())
                .contentType(JSON)
                .body(petJson)
                .when()
                .put(PET)
                .then()
                .spec(responseSpec());

        return this;
    }
}