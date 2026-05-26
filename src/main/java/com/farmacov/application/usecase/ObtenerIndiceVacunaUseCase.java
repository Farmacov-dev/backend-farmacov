package com.farmacov.application.usecase;

import com.farmacov.application.dto.IndiceSeguridadDto;
import com.farmacov.domain.models.IndiceSeguridadResult;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ObtenerIndiceVacunaUseCase {

    @Inject
    ReporteAdversoRepository reporteAdversoRepository;

    public IndiceSeguridadDto execute(Integer idVacuna) {
        IndiceSeguridadResult result = reporteAdversoRepository.getIndiceSeguridad(idVacuna);
        return toDto(result);
    }

    // -------------------------------------------------------------------------
    // Mapping — responsabilidad del use case: convierte el modelo de dominio
    // al DTO que expone la capa REST.
    // indiceSeguridad viene como BigDecimal desde el SP (DECIMAL(5,2) en MySQL);
    // lo convertimos a Double para el contrato del DTO de respuesta.
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