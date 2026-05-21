package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Roles;
import com.farmacov.infrastructure.entities.RolesEntity;

public class RolesMapper {

    // conviertir entidad a modelo
    // para cuando la db devuelve un registro y necesite pasarlo a la logica de negocio
    public static Roles toDomain(RolesEntity entity) {
        Roles roles = new Roles();
        roles.setId(entity.getId());
        roles.setNombre(entity.getNombre());
        roles.setEsAdmin(entity.isEsAdmin());
        roles.setPermisos(entity.getPermisos()); // adicion para el toggle de permisos
        return roles;
    }

    // convierte modelo a entidad
    // para  cuando hay datos listos en la logica de negocio y los quiero persistir en la db ;)
    public static RolesEntity toEntity(Roles roles) {
        RolesEntity entity = new RolesEntity();
        entity.setId(roles.getId());
        entity.setNombre(roles.getNombre());
        entity.setEsAdmin(roles.getEsAdmin() != null && roles.getEsAdmin());
        entity.setPermisos(roles.getPermisos()); // adicion para el toggle de permisos
        return entity;
    }
}
