package com.farmacov.domain.repository;

import com.farmacov.application.dto.IndiceSeguridadDto;
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
    IndiceSeguridadDto getIndiceSeguridad(Integer idVacuna);
    List<IndiceSeguridadDto> getAllIndiceSeguridad();


    // optimizacion: cambo de getAll y getEsGrave por los siguientes metodos:
    long countAll();
    long countByEsGrave(boolean esGrave);
    long countByMesYAnio(int mes, int anio);
    long countByIdSintoma(Integer idSintoma);




}
