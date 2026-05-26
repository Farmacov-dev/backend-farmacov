package com.farmacov.application.usecase;

import com.farmacov.application.dto.CrearVacunaDto;
import com.farmacov.domain.models.Vacuna;
import com.farmacov.domain.repository.FarmacoRepository;
import com.farmacov.domain.repository.VacunaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class VacunaUseCase {

    @Inject
    VacunaRepository vacunaRepository;

    @Inject
    FarmacoRepository farmacoRepository;

    public Vacuna crear(Integer id, CrearVacunaDto dto) {
        // El id es manual — verificamos que no exista ya
        vacunaRepository.findVacunaById(id).ifPresent(v -> {
            throw new jakarta.ws.rs.ClientErrorException(
                    "Ya existe una vacuna con id " + id, 409
            );
        });

        // Verificamos que el farmaco existe
        farmacoRepository.findFarmacoById(dto.getIdFarmaco())
                .orElseThrow(() -> new NotFoundException(
                        "Farmaco con id " + dto.getIdFarmaco() + " no encontrado"
                ));

        Vacuna nueva = new Vacuna();
        nueva.setIdVacuna(id);
        nueva.setIdFarmaco(dto.getIdFarmaco());
        nueva.setNombre(dto.getNombre());
        nueva.setFarmaceutica(dto.getFarmaceutica());
        nueva.setTipo(dto.getTipo());
        nueva.setDescripcionGeneral(dto.getDescripcionGeneral());

        return vacunaRepository.saveVacuna(nueva);
    }

    public Vacuna actualizar(Integer id, CrearVacunaDto dto) {
        // Verificamos que existe
        vacunaRepository.findVacunaById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Vacuna con id " + id + " no encontrada"
                ));

        Vacuna actualizada = new Vacuna();
        actualizada.setIdVacuna(id);
        actualizada.setNombre(dto.getNombre());
        actualizada.setFarmaceutica(dto.getFarmaceutica());
        actualizada.setTipo(dto.getTipo());
        actualizada.setDescripcionGeneral(dto.getDescripcionGeneral());

        return vacunaRepository.updateVacuna(actualizada);
    }

    public void eliminar(Integer id) {
        vacunaRepository.findVacunaById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Vacuna con id " + id + " no encontrada"
                ));

        // La validación de reportes está en el Repository
        vacunaRepository.deleteVacunaById(id);
    }
}