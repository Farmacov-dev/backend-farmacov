package com.farmacov.application.usecase;

import com.farmacov.application.dto.VacunaDetalleResponseDto;
import com.farmacov.domain.repository.VacunaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

// Caso de uso para GET /vacunas/{id}
// Devuelve el detalle completo de una vacuna incluyendo todos sus efectos secundarios
@ApplicationScoped
public class GetVacunaDetalleUseCase {

    @Inject
    VacunaRepository vacunaRepository;

    public VacunaDetalleResponseDto execute(Integer id) {
        return vacunaRepository.findVacunaById(id)
                .map(VacunaDetalleResponseDto::fromDomain)
                .orElseThrow(() -> new NotFoundException("Vacuna con id " + id + " no encontrada"));
    }
}
