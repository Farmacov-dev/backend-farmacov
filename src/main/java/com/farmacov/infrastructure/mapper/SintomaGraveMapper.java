package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.SintomaGrave;
import com.farmacov.infrastructure.entities.SintomaGraveEntity;

public class SintomaGraveMapper {

    public static SintomaGrave toDomain(SintomaGraveEntity entity) {
        SintomaGrave sintomaGrave = new SintomaGrave();
        sintomaGrave.setId(entity.getId()); // cambio de getid
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
        // vacuna ya no se setea aqui, es responsabilidad del RepositoryImpl
        // usa em.getReference() igual que en el patron
        entity.setNombre(sintomaGrave.getNombre());
        return entity;
    }
}