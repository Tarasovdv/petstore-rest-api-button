package ru.buttonone.petstore.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import ru.buttonone.petstore.utils.ConfigProp;

public class Spec {
    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(new ConfigProp().getProperty("base-url"))
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