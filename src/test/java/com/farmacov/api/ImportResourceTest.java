package com.farmacov.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.not;

@QuarkusTest
class ImportResourceTest {

    // imposible hacer mas pruebas debido a la naturaleza del endpoint

    @Test
    void importarReportes_sinArchivo_debeRetornar400() {
        // El método ejecutar() retorna 400 inmediatamente si archivo == null
        // Este caso no toca la BD — solo verifica la validación del Resource
        given()
                .contentType("multipart/form-data")
                .when().post("/admin/importar/reportes-adversos")
                .then()
                .statusCode(400);
    }

    @Test
    void importarEfectos_sinArchivo_debeRetornar400() {
        given()
                .contentType("multipart/form-data")
                .when().post("/admin/importar/efectos-secundarios")
                .then()
                .statusCode(400);
    }

    @Test
    void importarSintomas_sinArchivo_debeRetornar400() {
        given()
                .contentType("multipart/form-data")
                .when().post("/admin/importar/sintomas-graves")
                .then()
                .statusCode(400);
    }

    @Test
    void importarVacunas_sinArchivo_debeRetornar400() {
        given()
                .contentType("multipart/form-data")
                .when().post("/admin/importar/vacunas")
                .then()
                .statusCode(400);
    }

    @Test
    void importarFarmacos_sinArchivo_debeRetornar400() {
        given()
                .contentType("multipart/form-data")
                .when().post("/admin/importar/farmacos")
                .then()
                .statusCode(400);
    }

    @Test
    void importarVacunaCostos_sinArchivo_debeRetornar400() {
        given()
                .contentType("multipart/form-data")
                .when().post("/admin/importar/vacuna-costos")
                .then()
                .statusCode(400);
    }

    @Test
    void todosLosEndpoints_sonPublicos_noRequierenToken() {
        // /admin/importar está en PUBLIC_PATHS del AuthFilter
        given().contentType("multipart/form-data")
                .when().post("/admin/importar/reportes-adversos")
                .then().statusCode(not(401));
    }
}