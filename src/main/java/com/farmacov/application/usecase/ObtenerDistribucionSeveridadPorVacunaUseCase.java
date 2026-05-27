package com.farmacov.application.usecase;

import com.farmacov.application.dto.DistribucionSeveridadDto;
import com.farmacov.domain.repository.EfectoSecundarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;

@ApplicationScoped
public class ObtenerDistribucionSeveridadPorVacunaUseCase {

    @Inject
    EfectoSecundarioRepository efectoSecundarioRepository;

    public DistribucionSeveridadDto execute(Integer idVacuna) {
        Map<String, Long> distribucion =
                efectoSecundarioRepository.countBySeveridadForVacuna(idVacuna);

        DistribucionSeveridadDto dto = new DistribucionSeveridadDto();
        dto.setLeve(distribucion.getOrDefault("leve", 0L));
        dto.setModerado(distribucion.getOrDefault("moderado", 0L));
        dto.setGrave(distribucion.getOrDefault("grave", 0L));

        return dto;
    }
}