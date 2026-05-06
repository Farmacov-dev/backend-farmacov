package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.EfectoSecundario;
import com.farmacov.infrastructure.entities.EfectoSecundarioEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EfectoSecundarioMapperTest {

    @Test
    void toDomain_debeMapearTodosLosCampos() {
        // Arrange
        EfectoSecundarioEntity entity = new EfectoSecundarioEntity();
        entity.setId(1);
        VacunaEntity vacuna = new VacunaEntity();
        vacuna.setId(3);
        entity.setVacuna(vacuna);
        entity.setDescripcion("Dolor de cabeza");
        entity.setSeveridad(EfectoSecundarioEntity.Severidad.leve);

        // Act
        EfectoSecundario modelo = EfectoSecundarioMapper.toDomain(entity);

        // Assert
        assertEquals(1, modelo.getId());
        assertEquals(3, modelo.getIdVacuna());
        assertEquals("Dolor de cabeza", modelo.getDescripcion());
        assertEquals(EfectoSecundario.Severidad.leve, modelo.getSeveridad());
    }

    @Test
    void toEntity_debeMapearCamposBasicos_sinVacuna() {
        // Arrange
        EfectoSecundario modelo = new EfectoSecundario();
        modelo.setId(1);
        modelo.setIdVacuna(3);
        modelo.setDescripcion("Dolor de cabeza");
        modelo.setSeveridad(EfectoSecundario.Severidad.leve);

        // Act
        EfectoSecundarioEntity entity = EfectoSecundarioMapper.toEntity(modelo);

        // Assert
        assertEquals(1, entity.getId());
        assertEquals("Dolor de cabeza", entity.getDescripcion());
        assertEquals(EfectoSecundarioEntity.Severidad.leve, entity.getSeveridad());
        // null intencionalmente — el Repository asigna vacuna con em.getReference()
        assertNull(entity.getVacuna());
    }
}