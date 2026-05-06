package com.farmacov.domain.repository;

import com.farmacov.domain.models.VacunaCosto;

import java.util.List;
import java.util.Optional;

public interface VacunaCostoRepository {

    // guardar un costo nuevo en la base de datos
    VacunaCosto save(VacunaCosto vacunaCosto);

    // buscar todos los costos de una vacuna específica
    List<VacunaCosto> findByIdVacuna(Integer idVacuna);

    // buscar un costo por su ID
    Optional<VacunaCosto> findCostoById(Integer id);

    // actualizar el costo unitario de un registro existente
    VacunaCosto update(VacunaCosto vacunaCosto);

    // elimiar un costo por su ID
    void deleteCostoById(Integer id);
}