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
class VacunaCondicionResourceTest {

    @Inject
    EntityManager em;

    @BeforeEach
    @Transactional
    void limpiarEInsertar() {
        em.createNativeQuery("DELETE FROM vacuna_condiciones").executeUpdate();
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
                "INSERT INTO vacuna_condiciones (id, id_vacuna, temperatura, tiempo_ambiente, creado_en, actualizado_en) " +
                        "VALUES (1, 1, -70.0, 2.0, NOW(), NOW())"
        ).executeUpdate();
    }

    // ─── GET /admin/vacuna-condiciones/vacuna/{idVacuna} ─────────────────────

    @Test
    void getByVacuna_debeRetornar200ConLista() {
        given()
                .when().get("/admin/vacuna-condiciones/vacuna/1")
                .then()
                .statusCode(200)
                .body("$.size()", is(1))
                .body("[0].idVacuna", is(1))
                .body("[0].temperatura", is(-70.0f));
    }

    @Test
    void getByVacuna_vacunaInexistente_debeRetornar404() {
        given()
                .when().get("/admin/vacuna-condiciones/vacuna/999")
                .then()
                .statusCode(404);
    }

    // ─── GET /admin/vacuna-condiciones/{id} ──────────────────────────────────

    @Test
    void getById_debeRetornar200ConCondicion() {
        given()
                .when().get("/admin/vacuna-condiciones/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("temperatura", is(-70.0f))
                .body("tiempoAmbiente", is(2.0f));
    }

    @Test
    void getById_idInexistente_debeRetornar404() {
        given()
                .when().get("/admin/vacuna-condiciones/999")
                .then()
                .statusCode(404);
    }

    // ─── POST /admin/vacuna-condiciones ──────────────────────────────────────

    @Test
    void crear_debeRetornar201ConCondicionCreada() {
        given()
                .contentType("application/json")
                .body("{\"idVacuna\":1,\"temperatura\":2.0,\"tiempoAmbiente\":4.0}")
                .when().post("/admin/vacuna-condiciones")
                .then()
                .statusCode(201)
                .body("idVacuna", is(1))
                .body("temperatura", is(2.0f));
    }

    @Test
    void crear_sinTiempoAmbiente_debeRetornar201ConNullEnTiempoAmbiente() {
        given()
                .contentType("application/json")
                .body("{\"idVacuna\":1,\"temperatura\":2.0}")
                .when().post("/admin/vacuna-condiciones")
                .then()
                .statusCode(201)
                .body("temperatura", is(2.0f))
                .body("tiempoAmbiente", nullValue());
    }

    @Test
    void crear_vacunaInexistente_debeRetornar404() {
        given()
                .contentType("application/json")
                .body("{\"idVacuna\":999,\"temperatura\":2.0}")
                .when().post("/admin/vacuna-condiciones")
                .then()
                .statusCode(404);
    }

    @Test
    void crear_sinTemperatura_debeRetornar400() {
        given()
                .contentType("application/json")
                .body("{\"idVacuna\":1}")
                .when().post("/admin/vacuna-condiciones")
                .then()
                .statusCode(400);
    }

    @Test
    void crear_temperaturaFueraDeRango_debeRetornar400() {
        given()
                .contentType("application/json")
                .body("{\"idVacuna\":1,\"temperatura\":99999.9}")
                .when().post("/admin/vacuna-condiciones")
                .then()
                .statusCode(400);
    }

    // ─── PUT /admin/vacuna-condiciones/{id} ──────────────────────────────────

    @Test
    void editar_debeRetornar200ConCondicionActualizada() {
        given()
                .contentType("application/json")
                .body("{\"temperatura\":-80.0,\"tiempoAmbiente\":4.0}")
                .when().put("/admin/vacuna-condiciones/1")
                .then()
                .statusCode(200)
                .body("temperatura", is(-80.0f))
                .body("tiempoAmbiente", is(4.0f));
    }

    @Test
    void editar_conTiempoAmbienteNull_debeRetornar200YBorrarTiempoAmbiente() {
        given()
                .contentType("application/json")
                .body("{\"temperatura\":-80.0,\"tiempoAmbiente\":null}")
                .when().put("/admin/vacuna-condiciones/1")
                .then()
                .statusCode(200)
                .body("temperatura", is(-80.0f))
                .body("tiempoAmbiente", nullValue());
    }

    @Test
    void editar_idInexistente_debeRetornar404() {
        given()
                .contentType("application/json")
                .body("{\"temperatura\":-80.0}")
                .when().put("/admin/vacuna-condiciones/999")
                .then()
                .statusCode(404);
    }

    @Test
    void editar_sinTemperatura_debeRetornar400() {
        given()
                .contentType("application/json")
                .body("{\"tiempoAmbiente\":4.0}")
                .when().put("/admin/vacuna-condiciones/1")
                .then()
                .statusCode(400);
    }

    // ─── DELETE /admin/vacuna-condiciones/{id} ───────────────────────────────

    @Test
    void eliminar_debeRetornar204() {
        given()
                .when().delete("/admin/vacuna-condiciones/1")
                .then()
                .statusCode(204);
    }

    @Test
    void eliminar_idInexistente_debeRetornar404() {
        given()
                .when().delete("/admin/vacuna-condiciones/999")
                .then()
                .statusCode(404);
    }
}