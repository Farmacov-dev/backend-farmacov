package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.ReporteAdverso;
import com.farmacov.infrastructure.entities.ReporteAdversoEntity;

public class ReporteAdversoMapper {

    public static ReporteAdverso toDomain(ReporteAdversoEntity entity) {
        ReporteAdverso reporteAdverso = new ReporteAdverso();
        reporteAdverso.setId(entity.getId());
        // Solo se trasladan los ids al dominio para no acoplarlo a entidades JPA.
        if (entity.getVacuna() != null) {
            reporteAdverso.setIdVacuna(entity.getVacuna().getId());
        }
        if (entity.getSintomaGrave() != null) {
            reporteAdverso.setIdSintoma(entity.getSintomaGrave().getId());
        }
        reporteAdverso.setSexo(ReporteAdverso.Sexo.valueOf(entity.getSexo()));
        reporteAdverso.setGrupoEdad(ReporteAdverso.GrupoEdad.fromValue(entity.getGrupoEdad()));
        reporteAdverso.setEsGrave(entity.getEsGrave());
        reporteAdverso.setFechaReporte(entity.getFechaReporte());
        reporteAdverso.setCreadoEn(entity.getCreadoEn());
        return reporteAdverso;
    }

    public static ReporteAdversoEntity toEntity(ReporteAdverso reporteAdverso) {
        ReporteAdversoEntity entity = new ReporteAdversoEntity();
        entity.setId(reporteAdverso.getId());
        // Los enums se aplastan al valor de texto que queda persistido.
        entity.setSexo(reporteAdverso.getSexo().name());
        entity.setGrupoEdad(reporteAdverso.getGrupoEdad().getValue());
        entity.setEsGrave(reporteAdverso.getEsGrave());
        entity.setFechaReporte(reporteAdverso.getFechaReporte());
        entity.setCreadoEn(reporteAdverso.getCreadoEn());
        return entity;
    }
}
