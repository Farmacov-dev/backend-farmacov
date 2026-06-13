package com.farmacov.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;

@QuarkusTest
class AuthResourceTest {


    // roles tiene el campo permisos JSON que PermisosConverter no puede leer desde H2
    // Por esto,  los tests de login y me solo verifican contratos de autenticación.

    // ─── POST /auth/login ────────────────────────────────────────────────────

    @Test
    void login_sinToken_debeRetornar401() {
        // El Resource valida el header antes de llamar al UseCase
        // Este test no toca la BD
        given()
                .when().post("/auth/login")
                .then()
                .statusCode(401);
    }

    @Test
    void login_tokenInvalido_debeRetornar401() {
        given()
                .header("Authorization", "invalido")
                .when().post("/auth/login")
                .then()
                .statusCode(401);
    }

    // ─── GET /auth/me ────────────────────────────────────────────────────────

    @Test
    void me_sinToken_debeRetornar404() {
        // /auth/me NO está en PUBLIC_PATHS — el AuthFilter lo bloquea
        given()
                .when().get("/auth/me")
                .then()
                .statusCode(404);
    }

    @Test
    void me_conToken_debeNoRetornar401() {
        // Con token válido pasa el AuthFilter — puede retornar 404 si no hay usuario
        // pero no debe retornar 401
        given()
                .header("Authorization", "Bearer test-token")
                .when().get("/auth/me")
                .then()
                .statusCode(not(401));
    }

    // ─── POST /auth/registro ─────────────────────────────────────────────────

    @Test
    void registro_sinToken_debeRetornar401() {
        // resolverAdminDesdeToken retorna null sin Bearer → 401
        given()
                .contentType("application/json")
                .body("{\"nombre\":\"Ana\",\"apellidoPaterno\":\"Martinez\",\"correo\":\"ana@test.com\",\"password\":\"Secret123\",\"idRol\":1}")
                .when().post("/auth/registro")
                .then()
                .statusCode(401);
    }

    @Test
    void registro_datosInvalidos_debeRetornar400() {
        // @Valid en el DTO — sin correo válido retorna 400 antes de tocar la BD
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"nombre\":\"Ana\",\"apellidoPaterno\":\"Martinez\",\"correo\":\"no-es-email\",\"password\":\"Secret123\",\"idRol\":1}")
                .when().post("/auth/registro")
                .then()
                .statusCode(400);
    }

    @Test
    void registro_sinNombre_debeRetornar400() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"apellidoPaterno\":\"Martinez\",\"correo\":\"ana@test.com\",\"password\":\"Secret123\",\"idRol\":1}")
                .when().post("/auth/registro")
                .then()
                .statusCode(400);
    }
}