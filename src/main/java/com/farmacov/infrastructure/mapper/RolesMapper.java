package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Roles;
import com.farmacov.infrastructure.entities.RolesEntity;

public class RolesMapper {

    // Convierte entidad a modelo de dominio.
    // Se usa cuando la DB devuelve un registro y necesitas pasarlo a la lógica de negocio.
    public static Roles toDomain(RolesEntity entity) {
        Roles roles = new Roles();
        roles.setId(entity.getId());
        roles.setNombre(entity.getNombre());
        roles.setEsAdmin(entity.isEsAdmin());
        return roles;
    }

    // Convierte modelo de dominio a entidad.
    // Se usa cuando tienes datos listos en la lógica de negocio y los quieres persistir en la DB.
    public static RolesEntity toEntity(Roles roles) {
        RolesEntity entity = new RolesEntity();
        entity.setId(roles.getId());
        entity.setNombre(roles.getNombre());
        entity.setEsAdmin(roles.getEsAdmin());
        return entity;
    }
}
