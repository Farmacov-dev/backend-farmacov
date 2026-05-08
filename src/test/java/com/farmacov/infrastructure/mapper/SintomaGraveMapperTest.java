package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.SintomaGrave;
import com.farmacov.infrastructure.entities.SintomaGraveEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SintomaGraveMapperTest {

    // toDomain

    @Test
    void toDomain_debeMapearTodosLosCampos() {
        // Arrange
        SintomaGraveEntity entity = new SintomaGraveEntity();
        entity.setId(1);
        entity.setNombre("Anafilaxia");
        VacunaEntity vacuna = new VacunaEntity();
        vacuna.setId(3);
        entity.setVacuna(vacuna);

        // Act
        SintomaGrave modelo = SintomaGraveMapper.toDomain(entity);

        // Assert
        assertEquals(1, modelo.getId());
        assertEquals("Anafilaxia", modelo.getNombre());
        assertEquals(3, modelo.getIdVacuna());
    }

    @Test
    void toDomain_conVacunaNull_debeMapearIdVacunaNull() {
        // Arrange
        SintomaGraveEntity entity = new SintomaGraveEntity();
        entity.setId(1);
        entity.setNombre("Anafilaxia");
        entity.setVacuna(null);

        // Act
        SintomaGrave modelo = SintomaGraveMapper.toDomain(entity);

        // Assert
        assertEquals(1, modelo.getId());
        assertEquals("Anafilaxia", modelo.getNombre());
        assertNull(modelo.getIdVacuna()); // ← ternario devuelve null
    }

    // toEntity

    @Test
    void toEntity_debeMapearCamposBasicos_sinVacuna() {
        // Arrange
        SintomaGrave modelo = new SintomaGrave();
        modelo.setId(1);
        modelo.setIdVacuna(3);
        modelo.setNombre("Anafilaxia");

        // Act
        SintomaGraveEntity entity = SintomaGraveMapper.toEntity(modelo);

        // Assert
        assertEquals(1, entity.getId());
        assertEquals("Anafilaxia", entity.getNombre());
        // null intencionalmente, el Repository asigna vacuna con em.getReference()
        assertNull(entity.getVacuna());
    }
}