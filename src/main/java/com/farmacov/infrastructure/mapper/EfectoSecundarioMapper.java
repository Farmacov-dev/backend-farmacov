//paso 3 dspues de hacer models efecto secundari o
package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.EfectoSecundario;
import com.farmacov.infrastructure.entities.EfectoSecundarioEntity;

public class EfectoSecundarioMapper {

    // Convierte lo que viene de la base de datos a un objeto limpio de dominio.
    public static EfectoSecundario toDomain(EfectoSecundarioEntity entity) {
        EfectoSecundario modelo = new EfectoSecundario();

        modelo.setId(entity.getId());

        // Extraemos solo el ID de la vacuna — no necesitamos cargar
        // el objeto completo para el dominio
        modelo.setIdVacuna(entity.getVacuna().getId());

        modelo.setDescripcion(entity.getDescripcion());

        // Traducimos el enum de infraestructura al enum del dominio.
        // Usamos name() para obtener el texto del valor ("leve", "moderado", "grave")
        // y valueOf() para convertirlo al enum equivalente del dominio.
        // Si los valores no coinciden aquí, falla rápido y visible — no silenciosamente.
        modelo.setSeveridad(
                EfectoSecundario.Severidad.valueOf(entity.getSeveridad().name())
        );

        return modelo;
    }

    // Convierte el objeto de dominio a una Entity lista para persistir.
    public static EfectoSecundarioEntity toEntity(EfectoSecundario modelo) {
        EfectoSecundarioEntity entity = new EfectoSecundarioEntity();

        entity.setId(modelo.getId());

        // Reconstruimos la referencia a VacunaEntity con solo el ID.
        // Hibernate no necesita el objeto completo para respetar la FK.
       // VacunaEntity vacunaRef = new VacunaEntity();
       // vacunaRef.setId(modelo.getIdVacuna());
        //entity.setVacuna(vacunaRef);

        entity.setDescripcion(modelo.getDescripcion());

        // Traducción inversa — del enum del dominio al enum de infraestructura
        entity.setSeveridad(
                EfectoSecundarioEntity.Severidad.valueOf(modelo.getSeveridad().name())
        );

        return entity;
    }
}