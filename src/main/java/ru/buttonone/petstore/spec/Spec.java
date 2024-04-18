package ru.buttonone.petstore.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Spec {
    private static final String BASE_URI = "https://petstore.swagger.io/v2";

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .log(LogDetail.METHOD)
                .log(LogDetail.BODY)
                .build();
    }

    public static ResponseSpecification responseSpec() {
        return new ResponseSpecBuilder()
                .log(LogDetail.STATUS)
                .log(LogDetail.BODY)
                .expectStatusCode(200)
                .build();
    }
}