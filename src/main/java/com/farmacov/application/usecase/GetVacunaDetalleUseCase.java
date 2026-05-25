package com.farmacov.application.usecase;

import com.farmacov.application.dto.IndiceSeguridadDto;
import com.farmacov.application.dto.VacunaDetalleResponseDto;
import com.farmacov.domain.repository.VacunaRepository;
import com.farmacov.domain.repository.EfectoSecundarioRepository;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.Map;

// Caso de uso para GET /vacunas/{id}
// Devuelve el detalle completo de una vacuna incluyendo todos sus efectos secundarios
@ApplicationScoped
public class GetVacunaDetalleUseCase {

    @Inject
    VacunaRepository vacunaRepository;

    @Inject
    ReporteAdversoRepository reporteAdversoRepository;

    @Inject
    EfectoSecundarioRepository efectoSecundarioRepository;

    public VacunaDetalleResponseDto execute(Integer id) {
        VacunaDetalleResponseDto dto = vacunaRepository.findVacunaById(id)
                .map(VacunaDetalleResponseDto::fromDomain)
                .orElseThrow(() -> new NotFoundException("Vacuna con id " + id + " no encontrada"));


        try {
            IndiceSeguridadDto indice = reporteAdversoRepository.getIndiceSeguridad(id);
            dto.setIndiceSeguridad(indice.getIndiceSeguridad());
            dto.setTotalReportes(indice.getTotalReportes());
        } catch (Exception e) {
            // Si no hay reportes para esta vacuna, dejamos en null
            dto.setIndiceSeguridad(null);
            dto.setTotalReportes(0L);
        }

        Map<String, Long> distribucion =
                efectoSecundarioRepository.countBySeveridadForVacuna(id);
        dto.setDistribucionSeveridad(distribucion);

        return dto;


    }
}
