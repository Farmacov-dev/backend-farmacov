package com.farmacov.domain.repository;

import com.farmacov.domain.models.Vacuna;

import java.util.List;
import java.util.Optional;

public interface VacunaRepository {
    List<Vacuna> findAllVacunas();
    Optional<Vacuna> findVacunaById(Integer id);
}
