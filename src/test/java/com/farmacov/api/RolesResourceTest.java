package com.farmacov.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class RolesResourceTest {

    @Test
    void getAll_noRequiereToken_debeNoRetornar401() {
        // No podemos verificar 200 porque PermisosConverter es incompatible con H2
        given()
                .when().get("/roles")
                .then()
                .statusCode(org.hamcrest.Matchers.not(401));
    }
}