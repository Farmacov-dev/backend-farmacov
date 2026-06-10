package com.farmacov.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class StatusResourceTest {

    @Test
    void status_debeRetornar200() {
        given()
                .when().get("/status")
                .then()
                .statusCode(200);
    }

    @Test
    void status_debeTenerCampoStatusOk() {
        given()
                .when().get("/status")
                .then()
                .statusCode(200)
                .body("status", is("ok"));
    }

    @Test
    void status_debeTenerCampoApp() {
        given()
                .when().get("/status")
                .then()
                .statusCode(200)
                .body("app", is("farmacov-backend"));
    }
}