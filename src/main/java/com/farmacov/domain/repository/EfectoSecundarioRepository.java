//paso 4 despues de hacer mapper EfectoSecundarioMapper
package com.farmacov.domain.repository;

import com.farmacov.domain.models.EfectoSecundario;
import com.farmacov.domain.models.EfectoSecundario.Severidad;

import java.util.List;
import java.util.Optional;

public interface EfectoSecundarioRepository {

    // Registra un efecto secundario nuevo asociado a una vacuna
    EfectoSecundario save(EfectoSecundario efectoSecundario);

    // Obtiene todos los efectos secundarios de una vacuna específica.
    // Es el caso de uso más natural — ver qué efectos tiene una vacuna.
    List<EfectoSecundario> findByIdVacuna(Integer idVacuna);

    // Busca un efecto secundario por su ID.
    // Útil antes de actualizar o eliminar para verificar que existe.
    Optional<EfectoSecundario> findEfectoById(Integer id);

    // Filtra efectos secundarios de una vacuna por nivel de severidad.
    // Útil para dashboards — "muéstrame solo los efectos graves de esta vacuna"
    List<EfectoSecundario> findByIdVacunaAndSeveridad(Integer idVacuna, Severidad severidad);

    // Actualiza la descripción y/o severidad de un efecto existente
    EfectoSecundario update(EfectoSecundario efectoSecundario);

    // Elimina un efecto secundario por su ID
    void deleteEfectoById(Integer id);
}