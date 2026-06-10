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
class VacunaCostoResourceTest {

    @Inject
    EntityManager em;

    @BeforeEach
    @Transactional
    void limpiarEInsertar() {
        em.createNativeQuery("DELETE FROM vacuna_costos").executeUpdate();
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
                "INSERT INTO vacuna_costos (id, id_vacuna, costo_unitario, creado_en, actualizado_en) " +
                        "VALUES (1, 1, 299.99, NOW(), NOW())"
        ).executeUpdate();
    }

    // ─── GET /admin/vacuna-costos/vacuna/{idVacuna} ──────────────────────────

    @Test
    void getByVacuna_debeRetornar200ConLista() {
        given()
                .when().get("/admin/vacuna-costos/vacuna/1")
                .then()
                .statusCode(200)
                .body("$.size()", is(1))
                .body("[0].idVacuna", is(1))
                .body("[0].costoUnitario", is(299.99f));
    }

    @Test
    void getByVacuna_vacunaInexistente_debeRetornar404() {
        given()
                .when().get("/admin/vacuna-costos/vacuna/999")
                .then()
                .statusCode(404);
    }

    // ─── GET /admin/vacuna-costos/{id} ───────────────────────────────────────

    @Test
    void getById_debeRetornar200ConCosto() {
        given()
                .when().get("/admin/vacuna-costos/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("costoUnitario", is(299.99f));
    }

    @Test
    void getById_idInexistente_debeRetornar404() {
        given()
                .when().get("/admin/vacuna-costos/999")
                .then()
                .statusCode(404);
    }

    // ─── POST /admin/vacuna-costos ───────────────────────────────────────────

    @Test
    void crear_debeRetornar201ConCostoCreado() {
        given()
                .contentType("application/json")
                .body("{\"idVacuna\":1,\"costoUnitario\":250.00}")
                .when().post("/admin/vacuna-costos")
                .then()
                .statusCode(201)
                .body("idVacuna", is(1))
                .body("costoUnitario", is(250.00f));
    }

    @Test
    void crear_vacunaInexistente_debeRetornar404() {
        given()
                .contentType("application/json")
                .body("{\"idVacuna\":999,\"costoUnitario\":250.00}")
                .when().post("/admin/vacuna-costos")
                .then()
                .statusCode(404);
    }

    @Test
    void crear_costoNegativo_debeRetornar400() {
        given()
                .contentType("application/json")
                .body("{\"idVacuna\":1,\"costoUnitario\":-1.00}")
                .when().post("/admin/vacuna-costos")
                .then()
                .statusCode(400);
    }

    @Test
    void crear_sinIdVacuna_debeRetornar400() {
        given()
                .contentType("application/json")
                .body("{\"costoUnitario\":250.00}")
                .when().post("/admin/vacuna-costos")
                .then()
                .statusCode(400);
    }

    // ─── PUT /admin/vacuna-costos/{id} ───────────────────────────────────────

    @Test
    void editar_debeRetornar200ConCostoActualizado() {
        given()
                .contentType("application/json")
                .body("{\"costoUnitario\":225.00}")
                .when().put("/admin/vacuna-costos/1")
                .then()
                .statusCode(200)
                .body("costoUnitario", is(225.00f));
    }

    @Test
    void editar_idInexistente_debeRetornar404() {
        given()
                .contentType("application/json")
                .body("{\"costoUnitario\":225.00}")
                .when().put("/admin/vacuna-costos/999")
                .then()
                .statusCode(404);
    }

    @Test
    void editar_costoNegativo_debeRetornar400() {
        given()
                .contentType("application/json")
                .body("{\"costoUnitario\":-5.00}")
                .when().put("/admin/vacuna-costos/1")
                .then()
                .statusCode(400);
    }

    // ─── DELETE /admin/vacuna-costos/{id} ────────────────────────────────────

    @Test
    void eliminar_debeRetornar204() {
        given()
                .when().delete("/admin/vacuna-costos/1")
                .then()
                .statusCode(204);
    }

    @Test
    void eliminar_idInexistente_debeRetornar404() {
        given()
                .when().delete("/admin/vacuna-costos/999")
                .then()
                .statusCode(404);
    }
}