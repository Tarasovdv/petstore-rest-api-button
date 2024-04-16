package ru.buttonone.petstore.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static ru.buttonone.petstore.spec.Spec.*;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class PetService {

    public static final String PET_ID = "/pet/{petId}";
    public static final String PET_STATUS = "/pet/findByStatus";
    public static final String NEW_PET = "/pet";

    private boolean checkIfPetExistById(long petId) {
        Response response = given()
                .spec(requestSpec())
                .pathParam("petId", petId)
                .param("petId", petId)
                .when()
                .get(PET_ID);
        response
                .then()
                .log()
                .status();
        return response.getStatusCode() == 200;
    }

    @Step("Предоставление питомца по id = {id}")
    public PetService findPetById(long petId) {
        try {
            given()
                    .spec(requestSpec())
                    .pathParam("petId", petId)
                    .param("petId", petId)
                    .when()
                    .get(PET_ID)
                    .then()
                    .spec(responseSpec());
            return this;
        } catch (AssertionError assertionError) {
            throw new RuntimeException("Не удалось найти питомца по заданному id = " + petId);
        }
    }

    @Step("Удаление питомца по id = {petId}")
    public void deletePetById(long petId) {
        if (checkIfPetExistById(petId)) {
            try {
                given()
                        .spec(requestSpec())
                        .pathParam("petId", petId)
                        .param("petId", petId)
                        .when()
                        .delete(PET_ID)
                        .then()
                        .spec(responseSpec());
            } catch (AssertionError assertionError) {
                throw new RuntimeException("Не удалось удалить питомца по заданному id = " + petId);
            }
        }
    }

    @Step("Предоставление всех питомцев со статусом = {status}")
    public PetService findPetByStatus(String status) {
        try {
            given()
                    .spec(requestSpec())
                    .param("status", status)
                    .when()
                    .get(PET_STATUS)
                    .then()
                    .spec(responseSpec());
            return this;
        } catch (AssertionError assertionError) {
            throw new RuntimeException("Не удалось найти питомцев со статусом = " + status);
        }
    }

    @Step("Добавление нового питомца")
    public PetService addNewPet(long id, String petJson) {
        if (!checkIfPetExistById(id)) {
            try {
                given()
                        .spec(requestSpec())
                        .contentType(JSON)
                        .body(petJson)
                        .when()
                        .post(NEW_PET)
                        .then()
                        .spec(responseSpec());
                return this;
            } catch (AssertionError assertionError) {
                throw new RuntimeException("Не удалось добавить питомца");
            }
        } else {
            throw new RuntimeException("Питомец с id = " + id + " уже существует");
        }
    }

    @Step("Изменение имени на {name} и статуса питомца на {status} через id = {petId}")
    public PetService partialUpdatePet(long petId, String name, String status) {
        try {
            given()
                    .spec(requestSpec())
                    .pathParam("petId", petId)
                    .param("petId", petId)
                    .param("name", name)
                    .param("status", status)
                    .when()
                    .post(PET_ID)
                    .then()
                    .spec(responseSpec());
            return this;
        } catch (AssertionError assertionError) {
            throw new RuntimeException("Не удалось обновить имя и статус питомца");
        }
    }

    @Step("Полное изменение данных о питомце")
    public PetService fullUpdatePet(String petJson) {
        try {
            given()
                    .spec(requestSpec())
                    .contentType(JSON)
                    .body(petJson)
                    .when()
                    .put(NEW_PET)
                    .then()
                    .spec(responseSpec());
            return this;
        } catch (AssertionError assertionError) {
            throw new RuntimeException("Не удалось обновить данные о питомце");
        }
    }

    @Step("Проверка отсутствия данных о питомце с id = {petId} по запросу")
    public PetService checkNoDataAboutPet(long petId) {
        try {
            given()
                    .spec(requestSpec())
                    .pathParam("petId", petId)
                    .param("petId", petId)
                    .when()
                    .get(PET_ID)
                    .then()
                    .log().status()
                    .statusCode(404);
            return this;
        } catch (AssertionError assertionError) {
            throw new RuntimeException("Питомец с заданным id = " + petId + " не был удален");
        }
    }
}