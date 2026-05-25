package com.farmacov.domain.repository;

import com.farmacov.domain.models.Farmaco;

import java.util.List;
import java.util.Optional;

public interface FarmacoRepository {

    // Trae todos los fármacos registrados
    List<Farmaco> findAllFarmacos();

    // Busca un fármaco por su ID
    Optional<Farmaco> findFarmacoById(Integer id);

    // Guarda un fármaco nuevo
    Farmaco save(Farmaco farmaco);

    // Actualiza un fármaco existente
    Farmaco update(Farmaco farmaco);

    // Elimina un fármaco — el UseCase valida que no tenga vacunas asociadas
    void deleteFarmacoById(Integer id);
}