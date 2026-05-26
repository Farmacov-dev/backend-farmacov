package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Farmaco;
import com.farmacov.infrastructure.entities.FarmacoEntity;

public class FarmacoMapper {

    public static Farmaco toDomain(FarmacoEntity entity) {
        Farmaco modelo = new Farmaco();
        modelo.setId(entity.getId());
        modelo.setNombre(entity.getNombre());
        modelo.setTipo(entity.getTipo());
        modelo.setDescripcion(entity.getDescripcion());
        modelo.setCreadoEn(entity.getCreadoEn());
        modelo.setActualizadoEn(entity.getActualizadoEn());
        return modelo;
    }

    public static FarmacoEntity toEntity(Farmaco modelo) {
        FarmacoEntity entity = new FarmacoEntity();
        entity.setId(modelo.getId());
        entity.setNombre(modelo.getNombre());
        entity.setTipo(modelo.getTipo());
        entity.setDescripcion(modelo.getDescripcion());
        entity.setCreadoEn(modelo.getCreadoEn());
        entity.setActualizadoEn(modelo.getActualizadoEn());
        return entity;
    }
}