package com.farmacov.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;

@QuarkusTest
class AdminRolResourceTest {


    @Test
    void getRoles_esPublico_noRequiereToken() {
        // /admin/ está en PUBLIC_PATHS del AuthFilter
        given()
                .when().get("/admin/roles")
                .then()
                .statusCode(not(401));
    }

    @Test
    void crearRol_sinNombre_debeRetornar400() {
        // @Valid en CrearRolDto — falla antes de tocar la BD
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"esAdmin\":false}")
                .when().post("/admin/roles")
                .then()
                .statusCode(400);
    }

    @Test
    void editarRol_idInexistente_noRetorna401() {
        // Con token válido pasa el AuthFilter — puede retornar 404 o 500
        // pero no debe retornar 401
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"nombre\":\"test\",\"esAdmin\":false}")
                .when().put("/admin/roles/999")
                .then()
                .statusCode(not(401));
    }

    @Test
    void eliminarRol_idInexistente_noRetorna401() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().delete("/admin/roles/999")
                .then()
                .statusCode(not(401));
    }
}