package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Roles;
import com.farmacov.infrastructure.entities.RolesEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolesMapperTest {

    // toDomain

    @Test
    void toDomain_debeMapearTodosLosCampos() {
        // Arrange
        RolesEntity entity = new RolesEntity();
        entity.setId(1);
        entity.setNombre("ADMIN");
        entity.setEsAdmin(true);

        // Act
        Roles modelo = RolesMapper.toDomain(entity);

        // Assert
        assertEquals(1, modelo.getId());
        assertEquals("ADMIN", modelo.getNombre());
        assertTrue(modelo.getEsAdmin());
    }

    @Test
    void toDomain_conEsAdminFalse_debeMapearFalse() {
        // Arrange
        RolesEntity entity = new RolesEntity();
        entity.setId(2);
        entity.setNombre("USUARIO");
        entity.setEsAdmin(false);

        // Act
        Roles modelo = RolesMapper.toDomain(entity);

        // Assert
        assertFalse(modelo.getEsAdmin());
    }

    // toEntity

    @Test
    void toEntity_debeMapearTodosLosCampos() {
        // Arrange
        Roles modelo = new Roles();
        modelo.setId(1);
        modelo.setNombre("ADMIN");
        modelo.setEsAdmin(true);

        // Act
        RolesEntity entity = RolesMapper.toEntity(modelo);

        // Assert
        assertEquals(1, entity.getId());
        assertEquals("ADMIN", entity.getNombre());
        assertTrue(entity.isEsAdmin());
    }
    //esta prueba fue para detectar y eliminar el riesgo de NPE
   /* @Test
    void toEntity_conEsAdminNull_debeLanzarNullPointerException() {
        // Arrange
        Roles modelo = new Roles();
        modelo.setId(1);
        modelo.setNombre("ADMIN");
        modelo.setEsAdmin(null); // ← Boolean null → NPE al hacer unboxing

        // Act & Assert
        assertThrows(NullPointerException.class, () -> RolesMapper.toEntity(modelo));
    } */

    @Test
    void toEntity_conEsAdminNull_debeUsarFalsePorDefecto() {
        // Arrange
        Roles modelo = new Roles();
        modelo.setId(1);
        modelo.setNombre("ADMIN");
        modelo.setEsAdmin(null);

        // Act
        RolesEntity entity = RolesMapper.toEntity(modelo);

        // Assert
        assertFalse(entity.isEsAdmin()); // null , false por defecto
    }
}