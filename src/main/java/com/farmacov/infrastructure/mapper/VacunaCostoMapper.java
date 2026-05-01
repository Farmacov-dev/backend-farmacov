package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.VacunaCosto;
import com.farmacov.infrastructure.entities.VacunaCostoEntity;

public class VacunaCostoMapper {

    //convierte bd a un objeto en dominio
    //solo extrae ID de vacuna, no todo el objeto

    public static VacunaCosto toDomain(VacunaCostoEntity entity) {
        VacunaCosto modelo = new VacunaCosto();
        modelo.setId(entity.getId());
        modelo.setIdVacuna(entity.getVacuna().getId());
        modelo.setCostoUnitario(entity.getCostoUnitario());
        modelo.setCreadoEn(entity.getCreadoEn());
        modelo.setActualizadoEn(entity.getActualizadoEn());
        return modelo;
    }

    //convierte el objeto de dominio a una Entity lista para persistir.
    //la vacuna se reconstruye con solo el ID, no lo necesita hibernate
    //el objeto completo para respetar la FK, solo necesita la referencia.

    public static VacunaCostoEntity toEntity(VacunaCosto modelo) {
        VacunaCostoEntity entity = new VacunaCostoEntity();
        entity.setId(modelo.getId());

        VacunaEntity vacunaRef = new VacunaEntity();
        vacunaRef.setId(modelo.getIdVacuna());
        entity.setVacuna(vacunaRef);

        entity.setCostoUnitario(modelo.getCostoUnitario());
        entity.setCreadoEn(modelo.getCreadoEn());
        entity.setActualizadoEn(modelo.getActualizadoEn());
        return entity;
    }
}