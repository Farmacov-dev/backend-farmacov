package com.farmacov.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;

@QuarkusTest
class ResumenSintomasResourceTest {

    // Este endpoint usa la vista MySQL 'resumen_sintomas' a través de ResumenSintomasRepositoryImpl.
    // H2 no soporta vistas creadas en MySQL — cualquier llamada con datos retorna 500 en tests.

    @Test
    void get_esPublico_noRequiereToken() {
        // /dashboard/resumen-sintomas está en PUBLIC_PATHS del AuthFilter
        // Si alguien lo mueve fuera de PUBLIC_PATHS este test lo detecta
        given()
                .when().get("/dashboard/resumen-sintomas")
                .then()
                .statusCode(not(401));
    }

    @Test
    void get_conFiltros_noRequiereToken() {
        // Verifica que los query params no rompen el contrato de autenticación
        given()
                .queryParam("sexo", "F")
                .queryParam("grupoEdad", "18-29")
                .queryParam("esGrave", "true")
                .when().get("/dashboard/resumen-sintomas")
                .then()
                .statusCode(not(401));
    }
}