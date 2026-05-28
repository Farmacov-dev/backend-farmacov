package com.farmacov.domain.repository;

import com.farmacov.domain.models.Anotacion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnotacionRepository {
    // Contrato de persistencia para anotaciones.
    Anotacion save(Anotacion anotacion);

    Optional<Anotacion> findAnotacionById(Integer id);

    List<Anotacion> findAllAnotaciones();

    List<Anotacion> findByUsuarioId(UUID idUsuario);

    Anotacion update(Anotacion anotacion);

    void deleteAnotacionById(Integer id);
}
