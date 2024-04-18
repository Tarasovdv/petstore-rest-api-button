package ru.buttonone.petstore.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static ru.buttonone.petstore.api.Endpoint.*;
import static ru.buttonone.petstore.spec.Spec.*;

public class PetService {

    private boolean checkPetExistById(long petId) {
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
        if (checkPetExistById(petId)) {
            given()
                    .spec(requestSpec())
                    .pathParam("petId", petId)
                    .when()
                    .delete(PET_BY_ID)
                    .then()
                    .spec(responseSpec());
        }
    }

    @Step("Предоставление всех питомцев со статусом = {status}")
    public PetService findPetByStatus(String status) {
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
    public PetService addNewPet(long id, String petJson) {
        if (checkPetExistById(id)) throw new RuntimeException("Питомец с id = " + id + " уже существует");
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

    @Step("Проверка отсутствия данных о питомце с id = {petId} по запросу")
    public PetService checkNoDataAboutPet(long petId) {
        given()
                .spec(requestSpec())
                .pathParam("petId", petId)
                .when()
                .get(PET_BY_ID)
                .then()
                .log().status()
                .statusCode(404);
        return this;
    }
}