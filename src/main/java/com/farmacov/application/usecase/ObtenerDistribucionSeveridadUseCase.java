package com.farmacov.application.usecase;

import com.farmacov.application.dto.DistribucionSeveridadDto;
import com.farmacov.domain.repository.EfectoSecundarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;

@ApplicationScoped
public class ObtenerDistribucionSeveridadUseCase {

    @Inject
    EfectoSecundarioRepository efectoSecundarioRepository;

    public DistribucionSeveridadDto execute() {
        Map<String, Long> distribucion =
                efectoSecundarioRepository.countBySeveridadGlobal();

        DistribucionSeveridadDto dto = new DistribucionSeveridadDto();
        dto.setLeve(distribucion.getOrDefault("leve", 0L));
        dto.setModerado(distribucion.getOrDefault("moderado", 0L));
        dto.setGrave(distribucion.getOrDefault("grave", 0L));

        return dto;
    }
}