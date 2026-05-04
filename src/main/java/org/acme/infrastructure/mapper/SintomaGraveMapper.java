package org.acme.infrastructure.mapper;

import org.acme.domain.models.SintomaGrave;
import org.acme.infrastructure.entities.SintomaGraveEntity;

public class SintomaGraveMapper {
    public static SintomaGrave toDomain(SintomaGraveEntity entity){
        SintomaGrave sintomaGrave = new SintomaGrave();
        sintomaGrave.setId(entity.getid());
        sintomaGrave.setIdVacuna(entity.getIdVacuna());
        sintomaGrave.setNombre(entity.getNombre());
        return sintomaGrave;
    }

    public static SintomaGraveEntity toEntity(SintomaGrave sintomaGrave){
        SintomaGraveEntity entity = new SintomaGraveEntity();
        entity.setId(sintomaGrave.getId());
        entity.setIdVacuna(sintomaGrave.getIdVacuna());
        entity.setNombre(sintomaGrave.getNombre());
        return entity;
    }


}

