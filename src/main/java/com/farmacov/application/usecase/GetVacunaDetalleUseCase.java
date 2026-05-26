package com.farmacov.application.usecase;

import com.farmacov.application.dto.VacunaDetalleResponseDto;
import com.farmacov.domain.models.IndiceSeguridadResult;
import com.farmacov.domain.repository.EfectoSecundarioRepository;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import com.farmacov.domain.repository.VacunaRepository;
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
                .orElseThrow(() -> new NotFoundException(
                        "Vacuna con id " + id + " no encontrada"
                ));

        try {
            // Cambiado de IndiceSeguridadDto a IndiceSeguridadResult
            IndiceSeguridadResult indice = reporteAdversoRepository.getIndiceSeguridad(id);
            dto.setIndiceSeguridad(
                    indice.getIndiceSeguridad() != null
                            ? indice.getIndiceSeguridad().doubleValue()
                            : null
            );
            dto.setTotalReportes(indice.getTotalReportes());
        } catch (Exception e) {
            dto.setIndiceSeguridad(null);
            dto.setTotalReportes(0L);
        }

        Map<String, Long> distribucion =
                efectoSecundarioRepository.countBySeveridadForVacuna(id);
        dto.setDistribucionSeveridad(distribucion);

        return dto;
    }
}