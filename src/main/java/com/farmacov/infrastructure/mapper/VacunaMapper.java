package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Vacuna;
import com.farmacov.infrastructure.entities.EfectoSecundarioEntity;
import com.farmacov.infrastructure.entities.VacunaCondicionEntity;
import com.farmacov.infrastructure.entities.VacunaCostoEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import org.acme.infrastructure.entities.SintomaGraveEntity;

public class VacunaMapper {

    // convierte entidad a modelo
    // para cuando la db devuelve un registro y necesite pasarlo a la lógica de negocio
    public static Vacuna toDomain(VacunaEntity entity) {
        if (entity == null) return null;

        Vacuna vacuna = new Vacuna();

        // campos propios de vacunas
        vacuna.setIdVacuna(entity.getId());
        vacuna.setNombre(entity.getNombre());
        vacuna.setFarmaceutica(entity.getFarmaceutica());
        vacuna.setTipo(entity.getTipo());
        vacuna.setDescripcionGeneral(entity.getDescripcionGeneral());
        vacuna.setCreadoEn(entity.getCreadoEn());
        vacuna.setActualizadoEn(entity.getActualizadoEn());

        // vacunas_condiciones — OneToMany: tomamos el primer registro disponible
        // el orden lo define el JOIN FETCH en el repositorio (ORDER BY c.id ASC)
        if (entity.getCondiciones() != null && !entity.getCondiciones().isEmpty()) {
            VacunaCondicionEntity condicion = entity.getCondiciones().get(0);
            vacuna.setTemperatura(condicion.getTemperatura());
            vacuna.setTiempoAmbiente(condicion.getTiempoAmbiente());
        }

        // vacuna_costos — OneToMany: tomamos el primer registro disponible
        if (entity.getCostos() != null && !entity.getCostos().isEmpty()) {
            VacunaCostoEntity costo = entity.getCostos().get(0);
            vacuna.setCostoUnitario(costo.getCostoUnitario());
        }

        // efectos_secundarios — OneToMany: tomamos el primer registro disponible
        if (entity.getEfectosSecundarios() != null && !entity.getEfectosSecundarios().isEmpty()) {
            EfectoSecundarioEntity efecto = entity.getEfectosSecundarios().get(0);
            vacuna.setDescripcionEfecto(efecto.getDescripcion());
            vacuna.setSeveridadEfecto(
                    efecto.getSeveridad() != null
                            ? efecto.getSeveridad().name()
                            : null
            );
        }

        // sintomas_graves — OneToMany: tomamos el primer registro disponible
        if (entity.getSintomasGraves() != null && !entity.getSintomasGraves().isEmpty()) {
            SintomaGraveEntity sintoma = entity.getSintomasGraves().get(0);
            vacuna.setNombreSintomaGrave(sintoma.getNombre());
        }

        return vacuna;
    }

    // convierte modelo a entidad (solo campos propios de la tabla vacunas)
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
