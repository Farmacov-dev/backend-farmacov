package com.farmacov.api;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class KpisDashboardResourceTest {

    @Inject
    EntityManager em;

    @BeforeEach
    @Transactional
    void limpiarEInsertar() {
        // Limpiar en orden inverso a las FKs
        em.createNativeQuery("DELETE FROM reportes_adversos").executeUpdate();
        em.createNativeQuery("DELETE FROM vacunas").executeUpdate();
        em.createNativeQuery("DELETE FROM farmaco").executeUpdate();

        // Insertar farmaco
        em.createNativeQuery(
                "INSERT INTO farmaco (id, nombre, tipo, descripcion, creado_en, actualizado_en) " +
                        "VALUES (1, 'tozinameran', 'ARNm', 'desc', NOW(), NOW())"
        ).executeUpdate();

        // Insertar vacuna
        em.createNativeQuery(
                "INSERT INTO vacunas (id, id_farmaco, nombre, creado_en, actualizado_en) " +
                        "VALUES (1, 1, 'Pfizer', NOW(), NOW())"
        ).executeUpdate();

        // Insertar 5 reportes: 3 graves, 2 no graves
        // 2 del mes actual, 3 de meses anteriores
        LocalDate hoy = LocalDate.now();
        LocalDate mesAnterior = hoy.minusMonths(1);

        em.createNativeQuery(
                "INSERT INTO reportes_adversos (id, id_vacuna, sexo, grupo_edad, es_grave, fecha_reporte, creado_en) VALUES " +
                        "(1, 1, 'M', '18-29', true,  '" + hoy + "', NOW()), " +
                        "(2, 1, 'F', '30-49', true,  '" + hoy + "', NOW()), " +
                        "(3, 1, 'M', '50-64', true,  '" + mesAnterior + "', NOW()), " +
                        "(4, 1, 'F', '65+',   false, '" + mesAnterior + "', NOW()), " +
                        "(5, 1, 'U', '0-17',  false, '" + mesAnterior + "', NOW())"
        ).executeUpdate();
    }

    @Test
    void getKpis_debeRetornar200() {
        given()
                .when().get("/dashboard/kpis")
                .then()
                .statusCode(200);
    }

    @Test
    void getKpis_totalVacunas_debeSerCorrecto() {
        given()
                .when().get("/dashboard/kpis")
                .then()
                .statusCode(200)
                .body("totalVacunas", is(1));
    }

    @Test
    void getKpis_totalReportes_debeSerCorrecto() {
        given()
                .when().get("/dashboard/kpis")
                .then()
                .statusCode(200)
                .body("totalReportes", is(5));
    }

    @Test
    void getKpis_reportesEsteMes_debeContarSoloMesActual() {
        given()
                .when().get("/dashboard/kpis")
                .then()
                .statusCode(200)
                .body("reportesEsteMes", is(2));
    }

    @Test
    void getKpis_totalReportesGraves_debeSerCorrecto() {
        given()
                .when().get("/dashboard/kpis")
                .then()
                .statusCode(200)
                .body("totalReportesGraves", is(3));
    }

    @Test
    void getKpis_porcentajeReportesGraves_debeSerCorrecto() {
        // 3 graves / 5 total * 100 = 60.0
        given()
                .when().get("/dashboard/kpis")
                .then()
                .statusCode(200)
                .body("porcentajeReportesGraves", is(60.0f));
    }

}