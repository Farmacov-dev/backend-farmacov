package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Usuarios;
import com.farmacov.infrastructure.entities.UsuariosEntity;

public class UsuariosMapper {

    // Convierte entidad a modelo de dominio.
    // Se usa cuando la DB devuelve un registro y necesitas pasarlo a la lógica de negocio.
    public static Usuarios toDomain(UsuariosEntity entity) {
        Usuarios usuario = new Usuarios();
        usuario.setId(entity.getId());
        usuario.setFirebaseUuid(entity.getFirebaseUuid());
        usuario.setNombre(entity.getNombre());
        usuario.setApellidoPaterno(entity.getApellidoPaterno());
        usuario.setApellidoMaterno(entity.getApellidoMaterno());
        usuario.setCorreo(entity.getCorreo());
        usuario.setRol(RolesMapper.toDomain(entity.getRol()));
        usuario.setDepartamento(entity.getDepartamento());
        usuario.setCreadoEn(entity.getCreadoEn());
        usuario.setActualizadoEn(entity.getActualizadoEn());
        return usuario;
    }

    // Convierte modelo de dominio a entidad.
    // Se usa cuando tienes datos listos en la lógica de negocio y los quieres persistir en la DB.
    public static UsuariosEntity toEntity(Usuarios usuario) {
        UsuariosEntity entity = new UsuariosEntity();
        entity.setId(usuario.getId());
        entity.setFirebaseUuid(usuario.getFirebaseUuid());
        entity.setNombre(usuario.getNombre());
        entity.setApellidoPaterno(usuario.getApellidoPaterno());
        entity.setApellidoMaterno(usuario.getApellidoMaterno());
        entity.setCorreo(usuario.getCorreo());
        entity.setRol(RolesMapper.toEntity(usuario.getRol()));
        entity.setDepartamento(usuario.getDepartamento());
        entity.setCreadoEn(usuario.getCreadoEn());
        entity.setActualizadoEn(usuario.getActualizadoEn());
        return entity;
    }
}
