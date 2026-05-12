package com.farmacov.domain.repository;

import com.farmacov.domain.models.ReporteAdverso;

import java.util.List;
import java.util.Optional;

public interface ReporteAdversoRepository {
    ReporteAdverso save(ReporteAdverso reporteAdverso);
    Optional<ReporteAdverso> getById(Long id);
    List<ReporteAdverso> getAll();
    List<ReporteAdverso> getByIdVacuna(Integer idVacuna);
    List<ReporteAdverso> getByEsGrave(Boolean esGrave);

}
