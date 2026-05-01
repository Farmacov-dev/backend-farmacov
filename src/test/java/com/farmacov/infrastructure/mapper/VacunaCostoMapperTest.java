package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.VacunaCosto;
import com.farmacov.infrastructure.entities.VacunaCostoEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VacunaCostoMapperTest {

    @Test
    void toDomain_debeMapearTodosLosCampos() {
        // Arrange
        VacunaCostoEntity entity = new VacunaCostoEntity();
        entity.setId(1);
        VacunaEntity vacuna = new VacunaEntity();
        vacuna.setId(5);
        entity.setVacuna(vacuna);
        entity.setCostoUnitario(new BigDecimal("99.99"));
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());

        // Act
        VacunaCosto modelo = VacunaCostoMapper.toDomain(entity);

        // Assert
        assertEquals(1, modelo.getId());
        assertEquals(5, modelo.getIdVacuna());
        assertEquals(new BigDecimal("99.99"), modelo.getCostoUnitario());
        assertNotNull(modelo.getCreadoEn());
        assertNotNull(modelo.getActualizadoEn());
    }

    @Test
    void toEntity_debeMapearTodosLosCampos() {
        // Arrange
        VacunaCosto modelo = new VacunaCosto();
        modelo.setId(1);
        modelo.setIdVacuna(5);
        modelo.setCostoUnitario(new BigDecimal("99.99"));
        modelo.setCreadoEn(LocalDateTime.now());
        modelo.setActualizadoEn(LocalDateTime.now());

        // Act
        VacunaCostoEntity entity = VacunaCostoMapper.toEntity(modelo);

        // Assert
        assertEquals(1, entity.getId());
        assertEquals(new BigDecimal("99.99"), entity.getCostoUnitario());
        assertNotNull(entity.getCreadoEn());
        assertNotNull(entity.getActualizadoEn());
    }
}