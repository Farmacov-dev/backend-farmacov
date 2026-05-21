package com.farmacov.application.usecase;

import com.farmacov.application.dto.IndiceSeguridadDto;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ObtenerIndiceVacunaUseCase {

    @Inject
    ReporteAdversoRepository reporteAdversoRepository;

    public IndiceSeguridadDto execute(Integer idVacuna) {
        return reporteAdversoRepository.getIndiceSeguridad(idVacuna);
    }
}