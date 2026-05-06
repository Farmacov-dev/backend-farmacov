package com.farmacov.domain.repository;

import com.farmacov.domain.models.VacunaCondicion;

import java.util.List;
import java.util.Optional;

public interface VacunaCondicionRepository {

    // Registra una nueva condición de almacenamiento para una vacuna
    VacunaCondicion save(VacunaCondicion vacunaCondicion);

    // Obtiene todas las condiciones de una vacuna específica.
    // Es el caso de uso más natural para esta tabla.
    List<VacunaCondicion> findByIdVacuna(Integer idVacuna);

    // Busca una condición por su ID.
    // Útil antes de actualizar o eliminar para verificar que existe.
    Optional<VacunaCondicion> findCondicionById(Integer id);

    // Actualiza temperatura y/o tiempo ambiente de una condición existente
    VacunaCondicion update(VacunaCondicion vacunaCondicion);

    // Elimina una condición por su ID
    void deleteCondicionById(Integer id);
}