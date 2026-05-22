
package com.farmacov.application.usecase;

import com.farmacov.application.dto.ActualizarVacunaCondicionDto;
import com.farmacov.application.dto.CrearVacunaCondicionDto;
import com.farmacov.domain.models.VacunaCondicion;
import com.farmacov.domain.repository.VacunaCondicionRepository;
import com.farmacov.domain.repository.VacunaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class VacunaCondicionUseCase {

    @Inject
    VacunaCondicionRepository vacunaCondicionRepository;

    @Inject
    VacunaRepository vacunaRepository;

    // Registra una nueva condición de almacenamiento para una vacuna.
    // Valida que la vacuna existe antes de crear la condición.
    public VacunaCondicion crear(CrearVacunaCondicionDto dto) {
        vacunaRepository.findVacunaById(dto.getIdVacuna())
                .orElseThrow(() -> new NotFoundException(
                        "Vacuna con id " + dto.getIdVacuna() + " no encontrada"
                ));

        VacunaCondicion nueva = new VacunaCondicion();
        nueva.setIdVacuna(dto.getIdVacuna());
        nueva.setTemperatura(dto.getTemperatura());
        // tiempoAmbiente es nullable, puede llegar null desde el DTO

        nueva.setTiempoAmbiente(dto.getTiempoAmbiente());

        return vacunaCondicionRepository.save(nueva);
    }

    // Obtiene todas las condiciones de una vacuna específica.
    // Valida que la vacuna existe antes de buscar.
    public List<VacunaCondicion> obtenerPorVacuna(Integer idVacuna) {
        vacunaRepository.findVacunaById(idVacuna)
                .orElseThrow(() -> new NotFoundException(
                        "Vacuna con id " + idVacuna + " no encontrada"
                ));

        return vacunaCondicionRepository.findByIdVacuna(idVacuna);
    }

    // Busca una condición específica por su ID.
    public VacunaCondicion obtenerPorId(Integer id) {
        return vacunaCondicionRepository.findCondicionById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Condicion con id " + id + " no encontrada"
                ));
    }

    // Actualiza temperatura y/o tiempo ambiente de una condición existente.
    // tiempoAmbiente puede llegar null — es válido y borra el valor en la BD.
    public VacunaCondicion actualizar(Integer id, ActualizarVacunaCondicionDto dto) {
        VacunaCondicion existente = vacunaCondicionRepository.findCondicionById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Condicion con id " + id + " no encontrada"
                ));

        existente.setTemperatura(dto.getTemperatura());
        // null es valido aqui, borra el tiempo ambiente existente
        existente.setTiempoAmbiente(dto.getTiempoAmbiente());

        return vacunaCondicionRepository.update(existente);
    }

    // Elimina una condicion por su ID.
    public void eliminar(Integer id) {
        vacunaCondicionRepository.findCondicionById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Condicion con id " + id + " no encontrada"
                ));

        vacunaCondicionRepository.deleteCondicionById(id);
    }
}

