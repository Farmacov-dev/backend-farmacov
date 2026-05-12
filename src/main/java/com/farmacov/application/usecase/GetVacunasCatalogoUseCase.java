package com.farmacov.application.usecase;

import com.farmacov.application.dto.VacunaCatalogoResponseDto;
import com.farmacov.domain.repository.VacunaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

// Caso de uso para GET /vacunas
// Devuelve el catálogo completo de vacunas con los campos de resumen
@ApplicationScoped
public class GetVacunasCatalogoUseCase {

    @Inject
    VacunaRepository vacunaRepository;

    public List<VacunaCatalogoResponseDto> execute() {
        return vacunaRepository.findAllVacunas()
                .stream()
                // efectividad se pasa null hasta que se defina la lógica de cálculo
                .map(vacuna -> VacunaCatalogoResponseDto.fromDomain(vacuna, null))
                .collect(Collectors.toList());
    }
}
