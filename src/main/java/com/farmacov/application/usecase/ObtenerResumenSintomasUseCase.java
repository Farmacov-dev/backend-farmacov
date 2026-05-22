package com.farmacov.application.usecase;

import com.farmacov.application.dto.ResumenSintomasDto;
import com.farmacov.infrastructure.repository.ResumenSintomasRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ObtenerResumenSintomasUseCase {

    @Inject
    ResumenSintomasRepositoryImpl resumenSintomasRepository;

    public List<ResumenSintomasDto> execute(Integer idVacuna, String sexo,
                                            String grupoEdad, Boolean esGrave,  Integer limit) {
        return resumenSintomasRepository.filtrar(idVacuna, sexo, grupoEdad, esGrave, limit);
    }
}