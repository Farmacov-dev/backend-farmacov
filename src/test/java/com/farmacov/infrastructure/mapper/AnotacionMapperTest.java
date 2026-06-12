package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Anotacion;
import com.farmacov.infrastructure.entities.AnotacionEntity;
import com.farmacov.infrastructure.entities.UsuariosEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AnotacionMapperTest {

    // ─── toDomain ───────────────────────────────────────────

    @Test
    void toDomain_debeMapearTodosLosCampos() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UsuariosEntity usuario = new UsuariosEntity();
        usuario.setId(usuarioId);

        AnotacionEntity entity = new AnotacionEntity();
        entity.setId(1);
        entity.setUsuario(usuario);
        entity.setDashboardReferencia("dashboard-kpis");
        entity.setTitulo("Nota importante");
        entity.setObservaciones("Revisar datos del mes");
        LocalDateTime ahora = LocalDateTime.now();
        entity.setCreadoEn(ahora);
        entity.setActualizadoEn(ahora);

        // Act
        Anotacion modelo = AnotacionMapper.toDomain(entity);

        // Assert
        assertEquals(1, modelo.getId());
        assertEquals(usuarioId, modelo.getIdUsuario());
        assertEquals("dashboard-kpis", modelo.getDashboardReferencia());
        assertEquals("Nota importante", modelo.getTitulo());
        assertEquals("Revisar datos del mes", modelo.getObservaciones());
        assertEquals(ahora, modelo.getCreadoEn());
        assertEquals(ahora, modelo.getActualizadoEn());
    }

    @Test
    void toDomain_conUsuarioNull_debeMapearIdUsuarioNull() {
        // Arrange
        AnotacionEntity entity = new AnotacionEntity();
        entity.setId(1);
        entity.setUsuario(null); // ← null check en el mapper
        entity.setTitulo("Nota");
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());

        // Act
        Anotacion modelo = AnotacionMapper.toDomain(entity);

        // Assert
        assertNull(modelo.getIdUsuario());
    }

    @Test
    void toDomain_conCamposOpcionalesNull_debeMapearNull() {
        // dashboardReferencia y observaciones son nullable
        AnotacionEntity entity = new AnotacionEntity();
        entity.setId(1);
        entity.setUsuario(null);
        entity.setDashboardReferencia(null);
        entity.setTitulo("Nota");
        entity.setObservaciones(null);
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());

        // Act
        Anotacion modelo = AnotacionMapper.toDomain(entity);

        // Assert
        assertNull(modelo.getDashboardReferencia());
        assertNull(modelo.getObservaciones());
    }

    // ─── toEntity ───────────────────────────────────────────

    @Test
    void toEntity_debeMapearCamposBasicos_sinUsuario() {
        // Arrange
        Anotacion modelo = new Anotacion();
        modelo.setId(1);
        modelo.setIdUsuario(UUID.randomUUID());
        modelo.setDashboardReferencia("dashboard-kpis");
        modelo.setTitulo("Nota importante");
        modelo.setObservaciones("Revisar datos del mes");
        LocalDateTime ahora = LocalDateTime.now();
        modelo.setCreadoEn(ahora);
        modelo.setActualizadoEn(ahora);

        // Act
        AnotacionEntity entity = AnotacionMapper.toEntity(modelo);

        // Assert
        assertEquals(1, entity.getId());
        assertEquals("dashboard-kpis", entity.getDashboardReferencia());
        assertEquals("Nota importante", entity.getTitulo());
        assertEquals("Revisar datos del mes", entity.getObservaciones());
        assertEquals(ahora, entity.getCreadoEn());
        assertEquals(ahora, entity.getActualizadoEn());
        // usuario no se setea en el mapper — responsabilidad del RepositoryImpl
        assertNull(entity.getUsuario());
    }

    @Test
    void toEntity_conCamposOpcionalesNull_debeMapearNull() {
        // Arrange
        Anotacion modelo = new Anotacion();
        modelo.setId(1);
        modelo.setDashboardReferencia(null);
        modelo.setTitulo("Nota");
        modelo.setObservaciones(null);
        modelo.setCreadoEn(LocalDateTime.now());
        modelo.setActualizadoEn(LocalDateTime.now());

        // Act
        AnotacionEntity entity = AnotacionMapper.toEntity(modelo);

        // Assert
        assertNull(entity.getDashboardReferencia());
        assertNull(entity.getObservaciones());
        assertNull(entity.getUsuario());
    }
}