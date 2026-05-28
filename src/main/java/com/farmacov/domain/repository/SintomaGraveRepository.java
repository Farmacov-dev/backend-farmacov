package com.farmacov.domain.repository;

import com.farmacov.domain.models.SintomaGrave;

import java.util.List;
import java.util.Optional;

public interface SintomaGraveRepository {
    // Contrato de persistencia para sintomas graves; la implementacion concreta vive en infraestructura.

    SintomaGrave save(SintomaGrave sintomaGrave);
    Optional<SintomaGrave> getById(Integer id);
    List<SintomaGrave> getAll();

    List<SintomaGrave> findByIdVacuna(Integer vacuna);

    SintomaGrave update(SintomaGrave sintomaGrave);

    void deleteSintomaById(Integer id);

}
