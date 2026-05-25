package com.farmacov.application.usecase;

import com.farmacov.application.dto.VacunaCatalogoResponseDto;
import com.farmacov.domain.repository.VacunaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Caso de uso para GET /vacunas
// Devuelve el catálogo completo de vacunas con los campos de resumen
@ApplicationScoped
public class GetVacunasCatalogoUseCase {

    @Inject
    VacunaRepository vacunaRepository;

    public List<VacunaCatalogoResponseDto> execute() {
        // efectividad ahora viene del índice de seguridad calculado en la VIEW
        Map<Integer, BigDecimal> indicesPorVacuna = vacunaRepository.findIndicesSeguridad();

        return vacunaRepository.findAllVacunas()
                .stream()
                .map(vacuna -> VacunaCatalogoResponseDto.fromDomain(
                        vacuna,
                        indicesPorVacuna.getOrDefault(vacuna.getIdVacuna(), BigDecimal.ZERO)
                ))
                .collect(Collectors.toList());
    }
}