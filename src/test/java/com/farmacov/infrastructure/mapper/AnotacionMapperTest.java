package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Anotacion;
import com.farmacov.infrastructure.entities.AnotacionEntity;
import com.farmacov.infrastructure.entities.UsuariosEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AnotacionMapperTest {

    private static final LocalDateTime FECHA_FIJA =
            LocalDateTime.of(2026, Month.JANUARY, 15, 10, 0, 0);

    // ─── toDomain ───────────────────────────────────────────

    @Test
    void toDomain_debeMapearTodosLosCampos() {
        UUID usuarioId = UUID.randomUUID();
        UsuariosEntity usuario = new UsuariosEntity();
        usuario.setId(usuarioId);

        AnotacionEntity entity = new AnotacionEntity();
        entity.setId(1);
        entity.setUsuario(usuario);
        entity.setDashboardReferencia("dashboard-kpis");
        entity.setTitulo("Nota importante");
        entity.setObservaciones("Revisar datos del mes");
        entity.setCreadoEn(FECHA_FIJA);
        entity.setActualizadoEn(FECHA_FIJA);

        Anotacion modelo = AnotacionMapper.toDomain(entity);

        assertEquals(1, modelo.getId());
        assertEquals(usuarioId, modelo.getIdUsuario());
        assertEquals("dashboard-kpis", modelo.getDashboardReferencia());
        assertEquals("Nota importante", modelo.getTitulo());
        assertEquals("Revisar datos del mes", modelo.getObservaciones());
        assertEquals(FECHA_FIJA, modelo.getCreadoEn());
        assertEquals(FECHA_FIJA, modelo.getActualizadoEn());
    }

    @Test
    void toDomain_conUsuarioNull_debeMapearIdUsuarioNull() {
        AnotacionEntity entity = new AnotacionEntity();
        entity.setId(1);
        entity.setUsuario(null);
        entity.setTitulo("Nota");
        entity.setCreadoEn(FECHA_FIJA);
        entity.setActualizadoEn(FECHA_FIJA);

        Anotacion modelo = AnotacionMapper.toDomain(entity);

        assertNull(modelo.getIdUsuario());
    }

    @Test
    void toDomain_conCamposOpcionalesNull_debeMapearNull() {
        AnotacionEntity entity = new AnotacionEntity();
        entity.setId(1);
        entity.setUsuario(null);
        entity.setDashboardReferencia(null);
        entity.setTitulo("Nota");
        entity.setObservaciones(null);
        entity.setCreadoEn(FECHA_FIJA);
        entity.setActualizadoEn(FECHA_FIJA);

        Anotacion modelo = AnotacionMapper.toDomain(entity);

        assertNull(modelo.getDashboardReferencia());
        assertNull(modelo.getObservaciones());
    }

    // ─── toEntity ───────────────────────────────────────────

    @Test
    void toEntity_debeMapearCamposBasicos_sinUsuario() {
        Anotacion modelo = new Anotacion();
        modelo.setId(1);
        modelo.setIdUsuario(UUID.randomUUID());
        modelo.setDashboardReferencia("dashboard-kpis");
        modelo.setTitulo("Nota importante");
        modelo.setObservaciones("Revisar datos del mes");
        modelo.setCreadoEn(FECHA_FIJA);
        modelo.setActualizadoEn(FECHA_FIJA);

        AnotacionEntity entity = AnotacionMapper.toEntity(modelo);

        assertEquals(1, entity.getId());
        assertEquals("dashboard-kpis", entity.getDashboardReferencia());
        assertEquals("Nota importante", entity.getTitulo());
        assertEquals("Revisar datos del mes", entity.getObservaciones());
        assertEquals(FECHA_FIJA, entity.getCreadoEn());
        assertEquals(FECHA_FIJA, entity.getActualizadoEn());
        assertNull(entity.getUsuario());
    }

    @Test
    void toEntity_conCamposOpcionalesNull_debeMapearNull() {
        Anotacion modelo = new Anotacion();
        modelo.setId(1);
        modelo.setDashboardReferencia(null);
        modelo.setTitulo("Nota");
        modelo.setObservaciones(null);
        modelo.setCreadoEn(FECHA_FIJA);
        modelo.setActualizadoEn(FECHA_FIJA);

        AnotacionEntity entity = AnotacionMapper.toEntity(modelo);

        assertNull(entity.getDashboardReferencia());
        assertNull(entity.getObservaciones());
        assertNull(entity.getUsuario());
    }
}