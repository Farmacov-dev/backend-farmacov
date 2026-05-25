package com.farmacov.application.usecase;

import com.farmacov.application.dto.IndiceSeguridadDto;
import com.farmacov.domain.models.IndiceSeguridadResult;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ObtenerTodosLosIndicesUseCase {

    @Inject
    ReporteAdversoRepository reporteAdversoRepository;

    public List<IndiceSeguridadDto> execute() {
        return reporteAdversoRepository.getAllIndiceSeguridad()
                .stream()
                .map(this::toDto)
                .toList();
    }

    // -------------------------------------------------------------------------
    // Mapping — mismo contrato que ObtenerIndiceVacunaUseCase:
    // BigDecimal indiceSeguridad → Double para el DTO de respuesta.
    // -------------------------------------------------------------------------
    private IndiceSeguridadDto toDto(IndiceSeguridadResult r) {
        return new IndiceSeguridadDto(
                r.getIdVacuna(),
                r.getNombreVacuna(),
                r.getTotalReportes(),
                r.getReportesGraves(),
                r.getIndiceSeguridad() != null ? r.getIndiceSeguridad().doubleValue() : null
        );
    }
}