package com.farmacov.api;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class FarmacoResourceTest {

    @Inject
    EntityManager em;

    @BeforeEach
    @Transactional
    void limpiarEInsertar() {
        em.createNativeQuery("DELETE FROM vacunas").executeUpdate();
        em.createNativeQuery("DELETE FROM farmaco").executeUpdate();

        em.createNativeQuery(
                "INSERT INTO farmaco (id, nombre, tipo, descripcion, creado_en, actualizado_en) " +
                        "VALUES (1, 'tozinameran', 'ARNm', 'desc test', NOW(), NOW())"
        ).executeUpdate();
    }

    // ─── GET /admin/farmacos ─────────────────────────────────────────────────

    @Test
    void getAll_debeRetornar200ConLista() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().get("/admin/farmacos")
                .then()
                .statusCode(200)
                .body("$.size()", is(1))
                .body("[0].nombre", is("tozinameran"))
                .body("[0].tipo", is("ARNm"));
    }

    // ─── GET /admin/farmacos/{id} ────────────────────────────────────────────

    @Test
    void getById_debeRetornar200ConFarmaco() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().get("/admin/farmacos/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("nombre", is("tozinameran"))
                .body("tipo", is("ARNm"));
    }

    @Test
    void getById_idInexistente_debeRetornar404() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().get("/admin/farmacos/999")
                .then()
                .statusCode(404);
    }

    // ─── POST /admin/farmacos ────────────────────────────────────────────────

    @Test
    void crear_debeRetornar201ConFarmacoCreado() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"nombre\":\"elasomeran\",\"tipo\":\"ARNm\",\"descripcion\":\"desc\"}")
                .when().post("/admin/farmacos")
                .then()
                .statusCode(201)
                .body("nombre", is("elasomeran"))
                .body("tipo", is("ARNm"));
    }

    @Test
    void crear_sinNombre_debeRetornar400() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"tipo\":\"ARNm\"}")
                .when().post("/admin/farmacos")
                .then()
                .statusCode(400);
    }

    @Test
    void crear_sinTipo_debeRetornar400() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"nombre\":\"elasomeran\"}")
                .when().post("/admin/farmacos")
                .then()
                .statusCode(400);
    }

    @Test
    void crear_sinDescripcion_debeRetornar201() {
        // descripcion es opcional
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"nombre\":\"elasomeran\",\"tipo\":\"ARNm\"}")
                .when().post("/admin/farmacos")
                .then()
                .statusCode(201)
                .body("nombre", is("elasomeran"));
    }

    // ─── PUT /admin/farmacos/{id} ────────────────────────────────────────────

    @Test
    void editar_debeRetornar200ConFarmacoActualizado() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"nombre\":\"tozinameran actualizado\",\"tipo\":\"ARNm modificado\"}")
                .when().put("/admin/farmacos/1")
                .then()
                .statusCode(200)
                .body("nombre", is("tozinameran actualizado"))
                .body("tipo", is("ARNm modificado"));
    }

    @Test
    void editar_idInexistente_debeRetornar404() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"nombre\":\"test\",\"tipo\":\"test\"}")
                .when().put("/admin/farmacos/999")
                .then()
                .statusCode(404);
    }

    // ─── DELETE /admin/farmacos/{id} ─────────────────────────────────────────

    @Test
    void eliminar_debeRetornar204() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().delete("/admin/farmacos/1")
                .then()
                .statusCode(204);
    }

    @Test
    void eliminar_idInexistente_debeRetornar404() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().delete("/admin/farmacos/999")
                .then()
                .statusCode(404);
    }
}