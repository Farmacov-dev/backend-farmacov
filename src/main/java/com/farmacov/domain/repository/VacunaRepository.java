package com.farmacov.domain.repository;

import com.farmacov.domain.models.Vacuna;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VacunaRepository {
    List<Vacuna> findAllVacunas();
    Optional<Vacuna> findVacunaById(Integer id);
    long countVacunas();

    Vacuna saveVacuna(Vacuna vacuna);
    Vacuna updateVacuna(Vacuna vacuna);
    void deleteVacunaById(Integer id);
    // Devuelve Map directo — el dominio no conoce Object[]
    Map<Integer, BigDecimal> findIndicesSeguridad();
}
