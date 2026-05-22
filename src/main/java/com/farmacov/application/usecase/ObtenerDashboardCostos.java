package com.farmacov.application.usecase;

import com.farmacov.application.dto.CostosPorVacunaDto;
import com.farmacov.domain.repository.VacunaCostoRepository;
import com.farmacov.domain.repository.VacunaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class ObtenerDashboardCostos {

    @Inject
    VacunaCostoRepository vacunaCostoRepository; // traemos costos

    @Inject
    VacunaRepository vacunaRepository; // traemos nombre

    public List<CostosPorVacunaDto> execute() {
        return vacunaCostoRepository.findAllCostos() // llama al repo y devuelve una lista
                .stream() // convierte a flujo
                .map(costo -> {  // itero la lista (VacunaCosto, modelo) y transfortmo cada elemnto en un objeto en este caso CostosPorVacunaDto
                    String nombreVacuna = vacunaRepository.findVacunaById(costo.getIdVacuna()) // busca la vacuna por el id que trae el costo
                            .map(vacuna -> vacuna.getNombre())
                            .orElse("Vacuna no encontrada");
                    CostosPorVacunaDto dto = new CostosPorVacunaDto();
                    dto.setNombreVacuna(nombreVacuna); // asigna el nombre que se obtuvo en el repo
                    dto.setCostoUnitario(costo.getCostoUnitario()); // asigna el costo del modelo
                    return dto;
                })
                .collect(Collectors.toList());
    }




}
