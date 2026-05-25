package com.farmacov.domain.repository;

import com.farmacov.application.dto.ResumenSintomasDto;
import java.util.List;

public interface ResumenSintomasRepository {
    List<ResumenSintomasDto> filtrar(Integer idVacuna, String sexo,
                                     String grupoEdad, Boolean esGrave, Integer limit);
}