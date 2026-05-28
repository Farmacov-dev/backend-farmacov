package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Anotacion;
import com.farmacov.infrastructure.entities.AnotacionEntity;

public class AnotacionMapper {
    public static Anotacion toDomain(AnotacionEntity entity){
        Anotacion anotacion = new Anotacion();
        anotacion.setId(entity.getId());
        // El dominio conserva solo el UUID del usuario, no la relacion JPA completa.
        anotacion.setIdUsuario(entity.getUsuario() != null ? entity.getUsuario().getId() : null);
        anotacion.setDashboardReferencia(entity.getDashboardReferencia());
        anotacion.setTitulo(entity.getTitulo());
        anotacion.setObservaciones(entity.getObservaciones());
        anotacion.setCreadoEn(entity.getCreadoEn());
        anotacion.setActualizadoEn(entity.getActualizadoEn());
        return anotacion;
    }

    public static AnotacionEntity toEntity(Anotacion anotacion){
        AnotacionEntity entity = new AnotacionEntity();
        entity.setId(anotacion.getId());
        entity.setDashboardReferencia(anotacion.getDashboardReferencia());
        entity.setTitulo(anotacion.getTitulo());
        entity.setObservaciones(anotacion.getObservaciones());
        entity.setCreadoEn(anotacion.getCreadoEn());
        entity.setActualizadoEn(anotacion.getActualizadoEn());
        return entity;
    }
}
