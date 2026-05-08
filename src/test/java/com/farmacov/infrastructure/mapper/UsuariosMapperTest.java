package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Roles;
import com.farmacov.domain.models.Usuarios;
import com.farmacov.infrastructure.entities.RolesEntity;
import com.farmacov.infrastructure.entities.UsuariosEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuariosMapperTest {

    // toDomain

    @Test
    void toDomain_debeMapearTodosLosCampos() {
        // Arrange
        RolesEntity rolEntity = new RolesEntity();
        rolEntity.setId(1);
        rolEntity.setNombre("ADMIN");
        rolEntity.setEsAdmin(true);

        UsuariosEntity entity = new UsuariosEntity();
        UUID id = UUID.randomUUID();
        entity.setId(id);
        entity.setFirebaseUuid("firebase-uid-123");
        entity.setNombre("Juan");
        entity.setApellidoPaterno("García");
        entity.setApellidoMaterno("López");
        entity.setCorreo("juan@farmacov.com");
        entity.setRol(rolEntity);
        entity.setDepartamento("TI");
        LocalDateTime ahora = LocalDateTime.now();
        entity.setCreadoEn(ahora);
        entity.setActualizadoEn(ahora);

        // Act
        Usuarios modelo = UsuariosMapper.toDomain(entity);

        // Assert
        assertEquals(id, modelo.getId());
        assertEquals("firebase-uid-123", modelo.getFirebaseUuid());
        assertEquals("Juan", modelo.getNombre());
        assertEquals("García", modelo.getApellidoPaterno());
        assertEquals("López", modelo.getApellidoMaterno());
        assertEquals("juan@farmacov.com", modelo.getCorreo());
        assertNotNull(modelo.getRol());
        assertEquals(1, modelo.getRol().getId());
        assertEquals("ADMIN", modelo.getRol().getNombre());
        assertEquals("TI", modelo.getDepartamento());
        assertEquals(ahora, modelo.getCreadoEn());
        assertEquals(ahora, modelo.getActualizadoEn());
    }

    @Test
    void toDomain_conRolNull_debeMapearRolNull() {
        // Arrange
        UsuariosEntity entity = new UsuariosEntity();
        entity.setId(UUID.randomUUID());
        entity.setFirebaseUuid("firebase-uid-123");
        entity.setNombre("Juan");
        entity.setApellidoPaterno("García");
        entity.setApellidoMaterno(null);
        entity.setCorreo("juan@farmacov.com");
        entity.setRol(null); // ← null check del ternario
        entity.setDepartamento(null);
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());

        // Act
        Usuarios modelo = UsuariosMapper.toDomain(entity);

        // Assert
        assertNull(modelo.getRol());
        assertNull(modelo.getApellidoMaterno());
        assertNull(modelo.getDepartamento());
    }

    // toEntity

    @Test
    void toEntity_debeMapearCamposBasicos_sinRol() {
        // Arrange
        Roles rol = new Roles();
        rol.setId(1);
        rol.setNombre("ADMIN");
        rol.setEsAdmin(true);

        Usuarios modelo = new Usuarios();
        UUID id = UUID.randomUUID();
        modelo.setId(id);
        modelo.setFirebaseUuid("firebase-uid-123");
        modelo.setNombre("Juan");
        modelo.setApellidoPaterno("García");
        modelo.setApellidoMaterno("López");
        modelo.setCorreo("juan@farmacov.com");
        modelo.setRol(rol);
        modelo.setDepartamento("TI");
        LocalDateTime ahora = LocalDateTime.now();
        modelo.setCreadoEn(ahora);
        modelo.setActualizadoEn(ahora);

        // Act
        UsuariosEntity entity = UsuariosMapper.toEntity(modelo);

        // Assert
        assertEquals(id, entity.getId());
        assertEquals("firebase-uid-123", entity.getFirebaseUuid());
        assertEquals("Juan", entity.getNombre());
        assertEquals("García", entity.getApellidoPaterno());
        assertEquals("López", entity.getApellidoMaterno());
        assertEquals("juan@farmacov.com", entity.getCorreo());
        assertEquals("TI", entity.getDepartamento());
        assertEquals(ahora, entity.getCreadoEn());
        assertEquals(ahora, entity.getActualizadoEn());
        // null intencionalmente, el RepositoryImpl asigna rol con em.getReference()
        assertNull(entity.getRol());
    }

    @Test
    void toEntity_conNullablesOpcionales_debeMapearNull() {
        // Arrange
        Usuarios modelo = new Usuarios();
        modelo.setId(UUID.randomUUID());
        modelo.setFirebaseUuid("firebase-uid-123");
        modelo.setNombre("Juan");
        modelo.setApellidoPaterno("García");
        modelo.setApellidoMaterno(null); // nullable en schema
        modelo.setCorreo("juan@farmacov.com");
        modelo.setRol(null);
        modelo.setDepartamento(null); // nullable en schema
        modelo.setCreadoEn(LocalDateTime.now());
        modelo.setActualizadoEn(LocalDateTime.now());

        // Act
        UsuariosEntity entity = UsuariosMapper.toEntity(modelo);

        // Assert
        assertNull(entity.getApellidoMaterno());
        assertNull(entity.getDepartamento());
        assertNull(entity.getRol());
    }
}