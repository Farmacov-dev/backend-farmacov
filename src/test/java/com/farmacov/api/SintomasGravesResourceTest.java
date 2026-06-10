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
class SintomasGravesResourceTest {

    @Inject
    EntityManager em;

    @BeforeEach
    @Transactional
    void limpiarEInsertar() {
        em.createNativeQuery("DELETE FROM reportes_adversos").executeUpdate();
        em.createNativeQuery("DELETE FROM sintomas_graves").executeUpdate();
        em.createNativeQuery("DELETE FROM vacunas").executeUpdate();
        em.createNativeQuery("DELETE FROM farmaco").executeUpdate();

        em.createNativeQuery(
                "INSERT INTO farmaco (id, nombre, tipo, descripcion, creado_en, actualizado_en) " +
                        "VALUES (1, 'tozinameran', 'ARNm', 'desc', NOW(), NOW())"
        ).executeUpdate();

        em.createNativeQuery(
                "INSERT INTO vacunas (id, id_farmaco, nombre, creado_en, actualizado_en) " +
                        "VALUES (1, 1, 'Pfizer', NOW(), NOW())"
        ).executeUpdate();

        em.createNativeQuery(
                "INSERT INTO sintomas_graves (id, id_vacuna, nombre) " +
                        "VALUES (1, 1, 'Miocarditis')"
        ).executeUpdate();
    }

    // ─── GET /admin/sintomas-graves ──────────────────────────────────────────

    @Test
    void getAll_debeRetornar200ConLista() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().get("/admin/sintomas-graves")
                .then()
                .statusCode(200)
                .body("$.size()", is(1))
                .body("[0].nombre", is("Miocarditis"));
    }

    // ─── GET /admin/sintomas-graves/vacuna/{idVacuna} ────────────────────────

    @Test
    void getByVacuna_debeRetornar200ConLista() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().get("/admin/sintomas-graves/vacuna/1")
                .then()
                .statusCode(200)
                .body("$.size()", is(1))
                .body("[0].idVacuna", is(1))
                .body("[0].nombre", is("Miocarditis"));
    }

    @Test
    void getByVacuna_vacunaInexistente_debeRetornar404() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().get("/admin/sintomas-graves/vacuna/999")
                .then()
                .statusCode(404);
    }

    // ─── GET /admin/sintomas-graves/{id} ─────────────────────────────────────

    @Test
    void getById_debeRetornar200ConSintoma() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().get("/admin/sintomas-graves/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("nombre", is("Miocarditis"))
                .body("idVacuna", is(1));
    }

    @Test
    void getById_idInexistente_debeRetornar404() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().get("/admin/sintomas-graves/999")
                .then()
                .statusCode(404);
    }

    // ─── POST /admin/sintomas-graves ─────────────────────────────────────────

    @Test
    void crear_debeRetornar201ConSintomaCreado() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"idVacuna\":1,\"nombre\":\"Anafilaxia\"}")
                .when().post("/admin/sintomas-graves")
                .then()
                .statusCode(201)
                .body("idVacuna", is(1))
                .body("nombre", is("Anafilaxia"));
    }

    @Test
    void crear_vacunaInexistente_debeRetornar404() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"idVacuna\":999,\"nombre\":\"Anafilaxia\"}")
                .when().post("/admin/sintomas-graves")
                .then()
                .statusCode(404);
    }

    @Test
    void crear_sinNombre_debeRetornar400() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"idVacuna\":1}")
                .when().post("/admin/sintomas-graves")
                .then()
                .statusCode(400);
    }

    @Test
    void crear_nombreVacio_debeRetornar400() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"idVacuna\":1,\"nombre\":\"\"}")
                .when().post("/admin/sintomas-graves")
                .then()
                .statusCode(400);
    }

    @Test
    void crear_nombreDemasiadoLargo_debeRetornar400() {
        String nombreLargo = "A".repeat(151);
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"idVacuna\":1,\"nombre\":\"" + nombreLargo + "\"}")
                .when().post("/admin/sintomas-graves")
                .then()
                .statusCode(400);
    }

    // ─── PUT /admin/sintomas-graves/{id} ─────────────────────────────────────

    @Test
    void editar_debeRetornar200ConSintomaActualizado() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"nombre\":\"Miocarditis aguda\"}")
                .when().put("/admin/sintomas-graves/1")
                .then()
                .statusCode(200)
                .body("nombre", is("Miocarditis aguda"));
    }

    @Test
    void editar_idInexistente_debeRetornar404() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"nombre\":\"Miocarditis aguda\"}")
                .when().put("/admin/sintomas-graves/999")
                .then()
                .statusCode(404);
    }

    @Test
    void editar_sinNombre_debeRetornar400() {
        given()
                .header("Authorization", "Bearer test-token")
                .contentType("application/json")
                .body("{\"nombre\":\"\"}")
                .when().put("/admin/sintomas-graves/1")
                .then()
                .statusCode(400);
    }

    // ─── DELETE /admin/sintomas-graves/{id} ──────────────────────────────────

    @Test
    void eliminar_idInexistente_debeRetornar404() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().delete("/admin/sintomas-graves/999")
                .then()
                .statusCode(404);
    }

}