package com.farmacov.application.usecase;

import com.farmacov.application.dto.IndiceSeguridadDto;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ObtenerTodosLosIndicesUseCase {

    @Inject
    ReporteAdversoRepository reporteAdversoRepository;

    public List<IndiceSeguridadDto> execute() {
        return reporteAdversoRepository.getAllIndiceSeguridad();
    }
}