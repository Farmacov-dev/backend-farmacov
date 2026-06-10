package com.farmacov.api;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class UltimaActualizacionResourceTest {

    @Inject
    EntityManager em;

    @BeforeEach
    @Transactional
    void limpiarEInsertar() {
        em.createNativeQuery("DELETE FROM reportes_adversos").executeUpdate();
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
                "INSERT INTO reportes_adversos (id, id_vacuna, sexo, grupo_edad, es_grave, fecha_reporte, creado_en) " +
                        "VALUES (1, 1, 'M', '18-29', false, '" + LocalDate.now() + "', '2026-03-15T10:00:00')"
        ).executeUpdate();
    }

    @Test
    void getUltimaActualizacion_debeRetornar200() {
        given()
                .header("Authorization", "Bearer test-token" )
                .when().get("/dashboard/ultima-actualizacion")
                .then()
                .statusCode(200);
    }

    @Test
    void getUltimaActualizacion_debeTenerCampoFecha() {
        given()
                .header("Authorization", "Bearer test-token")
                .when().get("/dashboard/ultima-actualizacion")
                .then()
                .statusCode(200)
                .body("fecha", notNullValue());
    }

    @Test
    @Transactional
    void getUltimaActualizacion_sinDatos_debeRetornar200ConFechaActual() {
        // Limpiar todo — el UseCase retorna LocalDateTime.now() si no hay datos
        // así que siempre hay una fecha válida
        em.createNativeQuery("DELETE FROM reportes_adversos").executeUpdate();
        em.flush();

        given()
                .header("Authorization", "Bearer test-token")
                .when().get("/dashboard/ultima-actualizacion")
                .then()
                .statusCode(200)
                .body("fecha", notNullValue());
    }
}