package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.EfectoSecundario;
import com.farmacov.domain.models.Vacuna;
import com.farmacov.domain.models.Farmaco;
import com.farmacov.infrastructure.entities.VacunaCondicionEntity;
import com.farmacov.infrastructure.entities.VacunaCostoEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import com.farmacov.infrastructure.entities.FarmacoEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        // vacuna_condiciones — tomamos el primer registro (JOIN FETCH ordena por id ASC en el repositorio)
        if (entity.getCondiciones() != null && !entity.getCondiciones().isEmpty()) {
            VacunaCondicionEntity condicion = entity.getCondiciones().get(0);
            vacuna.setTemperatura(condicion.getTemperatura());
            vacuna.setTiempoAmbiente(condicion.getTiempoAmbiente());
        }

        // vacuna_costos — tomamos el primer registro (costo vigente)
        if (entity.getCostos() != null && !entity.getCostos().isEmpty()) {
            VacunaCostoEntity costo = entity.getCostos().get(0);
            vacuna.setCostoUnitario(costo.getCostoUnitario());
        }

        // efectos_secundarios — mapeamos la lista completa al modelo de dominio
        List<EfectoSecundario> efectos = (entity.getEfectosSecundarios() != null)
                ? entity.getEfectosSecundarios().stream()
                        .map(e -> {
                            EfectoSecundario ef = new EfectoSecundario();
                            ef.setId(e.getId());
                            ef.setIdVacuna(entity.getId());
                            ef.setDescripcion(e.getDescripcion());
                            ef.setSeveridad(
                                e.getSeveridad() != null
                                    ? EfectoSecundario.Severidad.valueOf(e.getSeveridad().name())
                                    : null
                            );
                            return ef;
                        })
                        .collect(Collectors.toList())
                : Collections.emptyList();

        vacuna.setEfectosSecundarios(efectos);

        if (entity.getFarmaco() != null) {
            vacuna.setIdFarmaco(entity.getFarmaco().getId());
            vacuna.setNombreFarmaco(entity.getFarmaco().getNombre());
        }


        return vacuna;
    }

    // convierte modelo a entidad (solo campos propios de la tabla vacunas)
    // para cuando hay datos listos en la lógica de negocio y los quiero persistir en la db
    public static VacunaEntity toEntity(Vacuna vacuna) {
        if (vacuna == null) return null;

        VacunaEntity entity = new VacunaEntity();
        entity.setId(vacuna.getIdVacuna());
        entity.setNombre(vacuna.getNombre());
        entity.setFarmaceutica(vacuna.getFarmaceutica());
        entity.setTipo(vacuna.getTipo());
        entity.setDescripcionGeneral(vacuna.getDescripcionGeneral());
        entity.setCreadoEn(vacuna.getCreadoEn());
        entity.setActualizadoEn(vacuna.getActualizadoEn());

        return entity;
    }
}
