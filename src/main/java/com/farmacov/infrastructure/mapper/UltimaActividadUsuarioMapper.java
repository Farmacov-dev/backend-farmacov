package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.UltimaActividadUsuario;
import com.farmacov.infrastructure.entities.UltimaActividadUsuarioEntity;

public final class UltimaActividadUsuarioMapper {

    private UltimaActividadUsuarioMapper() {
    }

    public static UltimaActividadUsuario toDomain(UltimaActividadUsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        UltimaActividadUsuario domain = new UltimaActividadUsuario();
        domain.setIdUsuario(entity.getIdUsuario());
        domain.setEndpoint(entity.getEndpoint());
        domain.setMetodoHttp(entity.getMetodoHttp());
        domain.setStatusCode(entity.getStatusCode());
        domain.setQueryString(entity.getQueryString());
        domain.setUserAgent(entity.getUserAgent());
        domain.setIpCliente(entity.getIpCliente());
        domain.setCreadoEn(entity.getCreadoEn());
        domain.setActualizadoEn(entity.getActualizadoEn());
        return domain;
    }

    public static UltimaActividadUsuarioEntity toEntity(UltimaActividadUsuario domain) {
        if (domain == null) {
            return null;
        }

        UltimaActividadUsuarioEntity entity = new UltimaActividadUsuarioEntity();
        entity.setIdUsuario(domain.getIdUsuario());
        entity.setEndpoint(domain.getEndpoint());
        entity.setMetodoHttp(domain.getMetodoHttp());
        entity.setStatusCode(domain.getStatusCode());
        entity.setQueryString(domain.getQueryString());
        entity.setUserAgent(domain.getUserAgent());
        entity.setIpCliente(domain.getIpCliente());
        entity.setCreadoEn(domain.getCreadoEn());
        entity.setActualizadoEn(domain.getActualizadoEn());
        return entity;
    }
}
