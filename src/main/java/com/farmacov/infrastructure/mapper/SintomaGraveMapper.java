package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.SintomaGrave;
import com.farmacov.infrastructure.entities.SintomaGraveEntity;

public class SintomaGraveMapper {

    public static SintomaGrave toDomain(SintomaGraveEntity entity) {
        SintomaGrave sintomaGrave = new SintomaGrave();
        sintomaGrave.setId(entity.getId());
        // En dominio solo dejamos el id de la vacuna para no acoplarlo a JPA.
        //ahora solo se exxtrae el id de la vacuna como en los mappers
        if (entity.getVacuna() != null) {
            sintomaGrave.setIdVacuna(entity.getVacuna() !=null ? entity.getVacuna().getId() : null);
        }
        sintomaGrave.setNombre(entity.getNombre());
        return sintomaGrave;
    }

    public static SintomaGraveEntity toEntity(SintomaGrave sintomaGrave) {
        SintomaGraveEntity entity = new SintomaGraveEntity();
        entity.setId(sintomaGrave.getId());
        // La relacion se completa en el repositorio con em.getReference() para respetar la FK.
        entity.setNombre(sintomaGrave.getNombre());
        return entity;
    }
}
