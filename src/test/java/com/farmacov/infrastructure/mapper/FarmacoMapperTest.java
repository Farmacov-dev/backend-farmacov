package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Farmaco;
import com.farmacov.infrastructure.entities.FarmacoEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FarmacoMapperTest {

    // ─── toDomain ───────────────────────────────────────────

    @Test
    void toDomain_debeMapearTodosLosCampos() {
        // Arrange
        FarmacoEntity entity = new FarmacoEntity();
        entity.setId(1);
        entity.setNombre("tozinameran");
        entity.setTipo("ARNm");
        entity.setDescripcion("Vacuna contra COVID-19");
        LocalDateTime ahora = LocalDateTime.now();
        entity.setCreadoEn(ahora);
        entity.setActualizadoEn(ahora);

        // Act
        Farmaco modelo = FarmacoMapper.toDomain(entity);

        // Assert
        assertEquals(1, modelo.getId());
        assertEquals("tozinameran", modelo.getNombre());
        assertEquals("ARNm", modelo.getTipo());
        assertEquals("Vacuna contra COVID-19", modelo.getDescripcion());
        assertEquals(ahora, modelo.getCreadoEn());
        assertEquals(ahora, modelo.getActualizadoEn());
    }

    @Test
    void toDomain_conDescripcionNull_debeMapearNull() {
        // descripcion es nullable en el schema
        FarmacoEntity entity = new FarmacoEntity();
        entity.setId(1);
        entity.setNombre("tozinameran");
        entity.setTipo("ARNm");
        entity.setDescripcion(null);
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());

        // Act
        Farmaco modelo = FarmacoMapper.toDomain(entity);

        // Assert
        assertNull(modelo.getDescripcion());
    }

    // ─── toEntity ───────────────────────────────────────────

    @Test
    void toEntity_debeMapearTodosLosCampos() {
        // Arrange
        Farmaco modelo = new Farmaco();
        modelo.setId(1);
        modelo.setNombre("tozinameran");
        modelo.setTipo("ARNm");
        modelo.setDescripcion("Vacuna contra COVID-19");
        LocalDateTime ahora = LocalDateTime.now();
        modelo.setCreadoEn(ahora);
        modelo.setActualizadoEn(ahora);

        // Act
        FarmacoEntity entity = FarmacoMapper.toEntity(modelo);

        // Assert
        assertEquals(1, entity.getId());
        assertEquals("tozinameran", entity.getNombre());
        assertEquals("ARNm", entity.getTipo());
        assertEquals("Vacuna contra COVID-19", entity.getDescripcion());
        assertEquals(ahora, entity.getCreadoEn());
        assertEquals(ahora, entity.getActualizadoEn());
    }

    @Test
    void toEntity_conDescripcionNull_debeMapearNull() {
        // Arrange
        Farmaco modelo = new Farmaco();
        modelo.setId(1);
        modelo.setNombre("tozinameran");
        modelo.setTipo("ARNm");
        modelo.setDescripcion(null);
        modelo.setCreadoEn(LocalDateTime.now());
        modelo.setActualizadoEn(LocalDateTime.now());

        // Act
        FarmacoEntity entity = FarmacoMapper.toEntity(modelo);

        // Assert
        assertNull(entity.getDescripcion());
    }
}