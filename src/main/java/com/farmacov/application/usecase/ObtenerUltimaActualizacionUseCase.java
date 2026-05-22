package com.farmacov.application.usecase;

import com.farmacov.application.dto.UltimaActualizacionDto;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

@ApplicationScoped
public class ObtenerUltimaActualizacionUseCase {

    @Inject
    ReporteAdversoRepository reporteAdversoRepository;

    public UltimaActualizacionDto execute() {
        LocalDateTime fecha = reporteAdversoRepository
                .findFechaUltimaActualizacion()
                .orElse(LocalDateTime.now());

        UltimaActualizacionDto dto = new UltimaActualizacionDto();
        dto.setFecha(fecha);
        return dto;
    }
}