package com.farmacov.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;

@QuarkusTest
class IndiceSeguridadResourceTest {
    //anadir mas pruebas despues de ver como funciona h2 con vistas
    @Test
    void getTodos_esPublico_noRequiereToken() {
        // /dashboard/indice-seguridad está en PUBLIC_PATHS del AuthFilter
        given()
                .when().get("/dashboard/indice-seguridad")
                .then()
                .statusCode(not(401));
    }

    @Test
    void getPorVacuna_esPublico_noRequiereToken() {
        // /dashboard/indice-seguridad/{id} también es público
        given()
                .when().get("/dashboard/indice-seguridad/1")
                .then()
                .statusCode(not(401));
    }
}