package com.farmacov.application.usecase;

import com.farmacov.application.dto.VacunaCatalogoResponseDto;
import com.farmacov.domain.models.IndiceSeguridadResult;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import com.farmacov.domain.repository.VacunaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Caso de uso para GET /vacunas
// Devuelve el catálogo completo de vacunas con temperatura, tiempoAmbiente,
// costoUnitario e indiceSeguridad (calculado vía vista_indice_seguridad).
@ApplicationScoped
public class GetVacunasCatalogoUseCase {

    @Inject
    VacunaRepository vacunaRepository;

    @Inject
    ReporteAdversoRepository reporteAdversoRepository;

    public List<VacunaCatalogoResponseDto> execute() {

        // Una sola query sobre la vista precalculada para obtener todos los índices.
        // Se construye un mapa idVacuna → Double para O(1) lookup por vacuna.
        Map<Integer, Double> indicesPorVacuna = reporteAdversoRepository
                .getAllIndiceSeguridad()
                .stream()
                .filter(r -> r.getIdVacuna() != null)
                .collect(Collectors.toMap(
                        IndiceSeguridadResult::getIdVacuna,
                        r -> r.getIndiceSeguridad() != null
                                ? r.getIndiceSeguridad().doubleValue()
                                : null,
                        // en caso de clave duplicada conservamos el primer valor
                        (existing, replacement) -> existing
                ));

        return vacunaRepository.findAllVacunas()
                .stream()
                .map(vacuna -> VacunaCatalogoResponseDto.fromDomain(
                        vacuna,
                        null,   // efectividad — pendiente de definir lógica de cálculo
                        indicesPorVacuna.get(vacuna.getIdVacuna())
                ))
                .collect(Collectors.toList());
    }
}