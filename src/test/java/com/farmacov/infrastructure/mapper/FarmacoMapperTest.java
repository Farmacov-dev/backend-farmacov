package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Farmaco;
import com.farmacov.infrastructure.entities.FarmacoEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class FarmacoMapperTest {

    private static final LocalDateTime FECHA_FIJA =
            LocalDateTime.of(2026, Month.JANUARY, 15, 10, 0, 0);

    // ─── toDomain ───────────────────────────────────────────

    @Test
    void toDomain_debeMapearTodosLosCampos() {
        FarmacoEntity entity = new FarmacoEntity();
        entity.setId(1);
        entity.setNombre("tozinameran");
        entity.setTipo("ARNm");
        entity.setDescripcion("Vacuna contra COVID-19");
        entity.setCreadoEn(FECHA_FIJA);
        entity.setActualizadoEn(FECHA_FIJA);

        Farmaco modelo = FarmacoMapper.toDomain(entity);

        assertEquals(1, modelo.getId());
        assertEquals("tozinameran", modelo.getNombre());
        assertEquals("ARNm", modelo.getTipo());
        assertEquals("Vacuna contra COVID-19", modelo.getDescripcion());
        assertEquals(FECHA_FIJA, modelo.getCreadoEn());
        assertEquals(FECHA_FIJA, modelo.getActualizadoEn());
    }

    @Test
    void toDomain_conDescripcionNull_debeMapearNull() {
        FarmacoEntity entity = new FarmacoEntity();
        entity.setId(1);
        entity.setNombre("tozinameran");
        entity.setTipo("ARNm");
        entity.setDescripcion(null);
        entity.setCreadoEn(FECHA_FIJA);
        entity.setActualizadoEn(FECHA_FIJA);

        Farmaco modelo = FarmacoMapper.toDomain(entity);

        assertNull(modelo.getDescripcion());
    }

    // ─── toEntity ───────────────────────────────────────────

    @Test
    void toEntity_debeMapearTodosLosCampos() {
        Farmaco modelo = new Farmaco();
        modelo.setId(1);
        modelo.setNombre("tozinameran");
        modelo.setTipo("ARNm");
        modelo.setDescripcion("Vacuna contra COVID-19");
        modelo.setCreadoEn(FECHA_FIJA);
        modelo.setActualizadoEn(FECHA_FIJA);

        FarmacoEntity entity = FarmacoMapper.toEntity(modelo);

        assertEquals(1, entity.getId());
        assertEquals("tozinameran", entity.getNombre());
        assertEquals("ARNm", entity.getTipo());
        assertEquals("Vacuna contra COVID-19", entity.getDescripcion());
        assertEquals(FECHA_FIJA, entity.getCreadoEn());
        assertEquals(FECHA_FIJA, entity.getActualizadoEn());
    }

    @Test
    void toEntity_conDescripcionNull_debeMapearNull() {
        Farmaco modelo = new Farmaco();
        modelo.setId(1);
        modelo.setNombre("tozinameran");
        modelo.setTipo("ARNm");
        modelo.setDescripcion(null);
        modelo.setCreadoEn(FECHA_FIJA);
        modelo.setActualizadoEn(FECHA_FIJA);

        FarmacoEntity entity = FarmacoMapper.toEntity(modelo);

        assertNull(entity.getDescripcion());
    }
}