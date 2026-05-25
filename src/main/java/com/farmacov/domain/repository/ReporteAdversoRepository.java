package com.farmacov.domain.repository;

import com.farmacov.domain.models.IndiceSeguridadResult;
import com.farmacov.domain.models.ReporteAdverso;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReporteAdversoRepository {
    ReporteAdverso save(ReporteAdverso reporteAdverso);
    Optional<ReporteAdverso> getById(Long id);
    List<ReporteAdverso> getAll();
    List<ReporteAdverso> getByIdVacuna(Integer idVacuna);
    List<ReporteAdverso> getByEsGrave(Boolean esGrave);
    Optional<LocalDateTime> findFechaUltimaActualizacion();

    // Índice de seguridad — SP para una vacuna, vista para todas
    IndiceSeguridadResult getIndiceSeguridad(Integer idVacuna);
    List<IndiceSeguridadResult> getAllIndiceSeguridad();

    // Contadores optimizados para KPI dashboard — no cargan entidades en memoria
    long countAll();
    long countByEsGrave(boolean esGrave);
    long countByMesYAnio(int mes, int anio);
}