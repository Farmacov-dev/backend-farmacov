package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Vacuna;
import com.farmacov.infrastructure.entities.VacunaEntity;

public class VacunaMapper {

    // convierte entidad a modelo
    // para cuando la db devuelve un registro y necesite pasarlo a la lógica de negocio
    public static Vacuna toDomain(VacunaEntity entity) {
        if (entity == null) return null;

        Vacuna vacuna = new Vacuna();

        // campos propios de vacunas
        vacuna.setIdVacuna(entity.getIdVacuna());
        vacuna.setNombre(entity.getNombre());
        vacuna.setFarmaceutica(entity.getFarmaceutica());
        vacuna.setTipo(entity.getTipo());
        vacuna.setDescripcionGeneral(entity.getDescripcionGeneral());
        vacuna.setCreadoEn(entity.getCreadoEn());
        vacuna.setActualizadoEn(entity.getActualizadoEn());

        // campos de vacunas_condiciones (OneToOne lazy — puede ser null si no se cargó)
        if (entity.getCondicion() != null) {
            vacuna.setTemperatura(entity.getCondicion().getTemperatura());
            vacuna.setTiempoAmbiente(entity.getCondicion().getTiempoAmbiente());
        }

        // campos de vacuna_costos (OneToOne lazy)
        if (entity.getCosto() != null) {
            vacuna.setCostoUnitario(entity.getCosto().getCostoUnitario());
        }

        // campos de efectos_secundarios (OneToOne lazy)
        if (entity.getEfectoSecundario() != null) {
            vacuna.setDescripcionEfecto(entity.getEfectoSecundario().getDescripcion());
            vacuna.setSeveridadEfecto(
                    entity.getEfectoSecundario().getSeveridad() != null
                            ? entity.getEfectoSecundario().getSeveridad().name()
                            : null
            );
        }

        // campos de sintomas_graves (OneToOne lazy)
        if (entity.getSintomaGrave() != null) {
            vacuna.setNombreSintomaGrave(entity.getSintomaGrave().getNombre());
        }

        return vacuna;
    }

    // convierte modelo a entidad (solo campos propios de vacunas)
    // para cuando hay datos listos en la lógica de negocio y los quiero persistir en la db
    public static VacunaEntity toEntity(Vacuna vacuna) {
        if (vacuna == null) return null;

        VacunaEntity entity = new VacunaEntity();
        entity.setIdVacuna(vacuna.getIdVacuna());
        entity.setNombre(vacuna.getNombre());
        entity.setFarmaceutica(vacuna.getFarmaceutica());
        entity.setTipo(vacuna.getTipo());
        entity.setDescripcionGeneral(vacuna.getDescripcionGeneral());
        entity.setCreadoEn(vacuna.getCreadoEn());
        entity.setActualizadoEn(vacuna.getActualizadoEn());

        return entity;
    }
}
