//parte 3 despues de hacer modelo VacunaCondicion
package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.VacunaCondicion;
import com.farmacov.infrastructure.entities.VacunaCondicionEntity;

public class VacunaCondicionMapper {

    // Convierte lo que viene de la base de datos a un objeto limpio de dominio.
    public static VacunaCondicion toDomain(VacunaCondicionEntity entity) {
        VacunaCondicion modelo = new VacunaCondicion();

        modelo.setId(entity.getId());

        // Extraemos solo el ID de la vacuna — no el objeto completo
        modelo.setIdVacuna(entity.getVacuna().getId());

        modelo.setTemperatura(entity.getTemperatura());

        // tiempoAmbiente puede ser null — BigDecimal lo maneja sin problema,
        // no necesitamos ningún chequeo especial, el setter acepta null
        modelo.setTiempoAmbiente(entity.getTiempoAmbiente());

        modelo.setCreadoEn(entity.getCreadoEn());
        modelo.setActualizadoEn(entity.getActualizadoEn());

        return modelo;
    }

    // Convierte el objeto de dominio a una Entity lista para persistir.
    public static VacunaCondicionEntity toEntity(VacunaCondicion modelo) {
        VacunaCondicionEntity entity = new VacunaCondicionEntity();

        entity.setId(modelo.getId());

        // Reconstruimos la referencia a VacunaEntity con solo el ID.
        // Hibernate no necesita el objeto completo para respetar la FK.
        //VacunaEntity vacunaRef = new VacunaEntity();
        //vacunaRef.setId(modelo.getIdVacuna());
        //entity.setVacuna(vacunaRef);

        entity.setTemperatura(modelo.getTemperatura());

        // Si tiempoAmbiente es null, Hibernate guardará NULL en MySQL —
        // exactamente lo que el schema permite para este campo
        entity.setTiempoAmbiente(modelo.getTiempoAmbiente());

        entity.setCreadoEn(modelo.getCreadoEn());
        entity.setActualizadoEn(modelo.getActualizadoEn());

        return entity;
    }
}