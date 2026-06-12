package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.UltimaActividadUsuario;
import com.farmacov.infrastructure.entities.UltimaActividadUsuarioEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UltimaActividadUsuarioMapperTest {

    // ─── toDomain ───────────────────────────────────────────

    @Test
    void toDomain_debeMapearTodosLosCampos() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UltimaActividadUsuarioEntity entity = new UltimaActividadUsuarioEntity();
        entity.setIdUsuario(usuarioId);
        entity.setEndpoint("/dashboard/kpis");
        entity.setMetodoHttp("GET");
        entity.setStatusCode(200);
        entity.setQueryString("filtro=true");
        entity.setUserAgent("Mozilla/5.0");
        entity.setIpCliente("192.168.1.1");
        LocalDateTime ahora = LocalDateTime.now();
        entity.setCreadoEn(ahora);
        entity.setActualizadoEn(ahora);

        // Act
        UltimaActividadUsuario domain = UltimaActividadUsuarioMapper.toDomain(entity);

        // Assert
        assertEquals(usuarioId, domain.getIdUsuario());
        assertEquals("/dashboard/kpis", domain.getEndpoint());
        assertEquals("GET", domain.getMetodoHttp());
        assertEquals(200, domain.getStatusCode());
        assertEquals("filtro=true", domain.getQueryString());
        assertEquals("Mozilla/5.0", domain.getUserAgent());
        assertEquals("192.168.1.1", domain.getIpCliente());
        assertEquals(ahora, domain.getCreadoEn());
        assertEquals(ahora, domain.getActualizadoEn());
    }

    @Test
    void toDomain_conEntityNull_debeRetornarNull() {
        assertNull(UltimaActividadUsuarioMapper.toDomain(null));
    }

    @Test
    void toDomain_conCamposOpcionalesNull_debeMapearNull() {
        // queryString, userAgent e ipCliente son nullable
        UltimaActividadUsuarioEntity entity = new UltimaActividadUsuarioEntity();
        entity.setIdUsuario(UUID.randomUUID());
        entity.setEndpoint("/status");
        entity.setMetodoHttp("GET");
        entity.setStatusCode(200);
        entity.setQueryString(null);
        entity.setUserAgent(null);
        entity.setIpCliente(null);
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());

        UltimaActividadUsuario domain = UltimaActividadUsuarioMapper.toDomain(entity);

        assertNull(domain.getQueryString());
        assertNull(domain.getUserAgent());
        assertNull(domain.getIpCliente());
    }

    // ─── toEntity ───────────────────────────────────────────

    @Test
    void toEntity_debeMapearTodosLosCampos() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        UltimaActividadUsuario domain = new UltimaActividadUsuario();
        domain.setIdUsuario(usuarioId);
        domain.setEndpoint("/dashboard/kpis");
        domain.setMetodoHttp("GET");
        domain.setStatusCode(200);
        domain.setQueryString("filtro=true");
        domain.setUserAgent("Mozilla/5.0");
        domain.setIpCliente("192.168.1.1");
        LocalDateTime ahora = LocalDateTime.now();
        domain.setCreadoEn(ahora);
        domain.setActualizadoEn(ahora);

        // Act
        UltimaActividadUsuarioEntity entity = UltimaActividadUsuarioMapper.toEntity(domain);

        // Assert
        assertEquals(usuarioId, entity.getIdUsuario());
        assertEquals("/dashboard/kpis", entity.getEndpoint());
        assertEquals("GET", entity.getMetodoHttp());
        assertEquals(200, entity.getStatusCode());
        assertEquals("filtro=true", entity.getQueryString());
        assertEquals("Mozilla/5.0", entity.getUserAgent());
        assertEquals("192.168.1.1", entity.getIpCliente());
        assertEquals(ahora, entity.getCreadoEn());
        assertEquals(ahora, entity.getActualizadoEn());
    }

    @Test
    void toEntity_conDomainNull_debeRetornarNull() {
        assertNull(UltimaActividadUsuarioMapper.toEntity(null));
    }

    @Test
    void toEntity_conCamposOpcionalesNull_debeMapearNull() {
        UltimaActividadUsuario domain = new UltimaActividadUsuario();
        domain.setIdUsuario(UUID.randomUUID());
        domain.setEndpoint("/status");
        domain.setMetodoHttp("GET");
        domain.setStatusCode(200);
        domain.setQueryString(null);
        domain.setUserAgent(null);
        domain.setIpCliente(null);
        domain.setCreadoEn(LocalDateTime.now());
        domain.setActualizadoEn(LocalDateTime.now());

        UltimaActividadUsuarioEntity entity = UltimaActividadUsuarioMapper.toEntity(domain);

        assertNull(entity.getQueryString());
        assertNull(entity.getUserAgent());
        assertNull(entity.getIpCliente());
    }
}