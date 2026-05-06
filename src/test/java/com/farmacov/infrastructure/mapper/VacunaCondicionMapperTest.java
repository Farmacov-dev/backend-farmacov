package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.VacunaCondicion;
import com.farmacov.infrastructure.entities.VacunaCondicionEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VacunaCondicionMapperTest {

    @Test
    void toDomain_debeMapearTodosLosCampos() {
        // Arrange
        VacunaCondicionEntity entity = new VacunaCondicionEntity();
        entity.setId(1);
        VacunaEntity vacuna = new VacunaEntity();
        vacuna.setId(3);
        entity.setVacuna(vacuna);
        entity.setTemperatura(new BigDecimal("2.0"));
        entity.setTiempoAmbiente(new BigDecimal("4.0"));
        LocalDateTime ahora = LocalDateTime.now();
        entity.setCreadoEn(ahora);
        entity.setActualizadoEn(ahora);

        // Act
        VacunaCondicion modelo = VacunaCondicionMapper.toDomain(entity);

        // Assert
        assertEquals(1, modelo.getId());
        assertEquals(3, modelo.getIdVacuna());
        assertEquals(new BigDecimal("2.0"), modelo.getTemperatura());
        assertEquals(new BigDecimal("4.0"), modelo.getTiempoAmbiente());
        assertEquals(ahora, modelo.getCreadoEn());
        assertEquals(ahora, modelo.getActualizadoEn());
    }

    @Test
    void toDomain_conTiempoAmbienteNull_debeMapearNull() {
        // Arrange
        VacunaCondicionEntity entity = new VacunaCondicionEntity();
        entity.setId(1);
        VacunaEntity vacuna = new VacunaEntity();
        vacuna.setId(3);
        entity.setVacuna(vacuna);
        entity.setTemperatura(new BigDecimal("2.0"));
        entity.setTiempoAmbiente(null);
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());

        // Act
        VacunaCondicion modelo = VacunaCondicionMapper.toDomain(entity);

        // Assert
        assertNull(modelo.getTiempoAmbiente());
    }

    @Test
    void toEntity_debeMapearCamposBasicos_sinVacuna() {
        // Arrange
        VacunaCondicion modelo = new VacunaCondicion();
        modelo.setId(1);
        modelo.setIdVacuna(3);
        modelo.setTemperatura(new BigDecimal("2.0"));
        modelo.setTiempoAmbiente(new BigDecimal("4.0"));
        LocalDateTime ahora = LocalDateTime.now();
        modelo.setCreadoEn(ahora);
        modelo.setActualizadoEn(ahora);

        // Act
        VacunaCondicionEntity entity = VacunaCondicionMapper.toEntity(modelo);

        // Assert
        assertEquals(1, entity.getId());
        assertEquals(new BigDecimal("2.0"), entity.getTemperatura());
        assertEquals(new BigDecimal("4.0"), entity.getTiempoAmbiente());
        assertEquals(ahora, entity.getCreadoEn());
        assertEquals(ahora, entity.getActualizadoEn());
        // null intencionalmente — el Repository asigna vacuna con em.getReference()
        assertNull(entity.getVacuna());
    }

    @Test
    void toEntity_conTiempoAmbienteNull_debeMapearNull() {
        // Arrange
        VacunaCondicion modelo = new VacunaCondicion();
        modelo.setId(1);
        modelo.setIdVacuna(3);
        modelo.setTemperatura(new BigDecimal("2.0"));
        modelo.setTiempoAmbiente(null);
        modelo.setCreadoEn(LocalDateTime.now());
        modelo.setActualizadoEn(LocalDateTime.now());

        // Act
        VacunaCondicionEntity entity = VacunaCondicionMapper.toEntity(modelo);

        // Assert
        assertNull(entity.getTiempoAmbiente());
        assertNull(entity.getVacuna());
    }
}