package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.VacunaCosto;
import com.farmacov.infrastructure.entities.VacunaCostoEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
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
        LocalDateTime ahora = LocalDateTime.now();
        entity.setCreadoEn(ahora);
        entity.setActualizadoEn(ahora);

        // Act
        VacunaCosto modelo = VacunaCostoMapper.toDomain(entity);

        // Assert
        assertEquals(1, modelo.getId());
        assertEquals(5, modelo.getIdVacuna());
        assertEquals(new BigDecimal("99.99"), modelo.getCostoUnitario());
        assertEquals(ahora, modelo.getCreadoEn());
        assertEquals(ahora, modelo.getActualizadoEn());
    }

    @Test
    void toEntity_debeMapearCamposBasicos_sinVacuna() {
        // Arrange
        VacunaCosto modelo = new VacunaCosto();
        modelo.setId(1);
        modelo.setIdVacuna(5);
        modelo.setCostoUnitario(new BigDecimal("99.99"));
        LocalDateTime ahora = LocalDateTime.now();
        modelo.setCreadoEn(ahora);
        modelo.setActualizadoEn(ahora);

        // Act
        VacunaCostoEntity entity = VacunaCostoMapper.toEntity(modelo);

        // Assert
        assertEquals(1, entity.getId());
        assertEquals(new BigDecimal("99.99"), entity.getCostoUnitario());
        assertEquals(ahora, entity.getCreadoEn());
        assertEquals(ahora, entity.getActualizadoEn());
        // null intencionalmente — el Repository asigna vacuna con em.getReference()
        assertNull(entity.getVacuna());
    }
}