package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Bitacora;
import com.farmacov.infrastructure.entities.BitacoraEntity;

public class BitacoraMapper {

    public static Bitacora toDomain(BitacoraEntity entity) {
        Bitacora bitacora = new Bitacora();
        bitacora.setId(entity.getId());
        bitacora.setIdAdmin(entity.getAdmin().getId());
        bitacora.setAccion(Bitacora.AccionEnum.valueOf(entity.getAccion()));
        bitacora.setIdUsuarioAfectado(entity.getUsuarioAfectado().getId());
        bitacora.setCreadoEn(entity.getCreadoEn());
        return bitacora;
    }

    public static BitacoraEntity toEntity(Bitacora bitacora) {
        BitacoraEntity entity = new BitacoraEntity();
        entity.setAccion(bitacora.getAccion().name());
        // admin y usuarioAfectado son asignados en el repositorio via getReference
        return entity;
    }
}
