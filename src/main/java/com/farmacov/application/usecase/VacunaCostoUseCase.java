//quitar comentado cuando VacunaRepositoryImpl exista y se confirme

package com.farmacov.application.usecase;

import com.farmacov.application.dto.ActualizarVacunaCostoDto;
import com.farmacov.application.dto.CrearVacunaCostoDto;
import com.farmacov.domain.models.VacunaCosto;
import com.farmacov.domain.repository.VacunaCostoRepository;
import com.farmacov.domain.repository.VacunaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class VacunaCostoUseCase {

    // Repositorio de costos,  contrato definido en capa 4
    @Inject
    VacunaCostoRepository vacunaCostoRepository;

    // Repositorio de vacunas, para validar que la vacuna existe
    // antes de crear un costo asociado a ella
    @Inject
    VacunaRepository vacunaRepository;

    // Registra un nuevo costo para una vacuna.
    // Primero valida que la vacuna existe, si no, lanza 404.
    public VacunaCosto crear(CrearVacunaCostoDto dto) {
        // Validamos que la vacuna existe antes de crear el costo
        vacunaRepository.findVacunaById(dto.getIdVacuna())
                .orElseThrow(() -> new NotFoundException(
                        "Vacuna con id " + dto.getIdVacuna() + " no encontrada"
                ));

        VacunaCosto nuevo = new VacunaCosto();
        nuevo.setIdVacuna(dto.getIdVacuna());
        nuevo.setCostoUnitario(dto.getCostoUnitario());
        // Los timestamps los asigna el Repository en el momento del INSERT
        // pero los inicializamos aquí para que el modelo esté completo
        nuevo.setCreadoEn(LocalDateTime.now());
        nuevo.setActualizadoEn(LocalDateTime.now());

        return vacunaCostoRepository.save(nuevo);
    }

    // Obtiene todos los costos de una vacuna especifica.
    // Valida que la vacuna existe antes de buscar.
    public List<VacunaCosto> obtenerPorVacuna(Integer idVacuna) {
        vacunaRepository.findVacunaById(idVacuna)
                .orElseThrow(() -> new NotFoundException(
                        "Vacuna con id " + idVacuna + " no encontrada"
                ));

        return vacunaCostoRepository.findByIdVacuna(idVacuna);
    }

    // Busca un costo específico por su ID.
    public VacunaCosto obtenerPorId(Integer id) {
        return vacunaCostoRepository.findCostoById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Costo con id " + id + " no encontrado"
                ));
    }

    // Actualiza el costo unitario de un registro existente.
    public VacunaCosto actualizar(Integer id, ActualizarVacunaCostoDto dto) {
        // Verificamos que el costo existe antes de actualizar
        VacunaCosto existente = vacunaCostoRepository.findCostoById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Costo con id " + id + " no encontrado"
                ));

        existente.setCostoUnitario(dto.getCostoUnitario());
        existente.setActualizadoEn(LocalDateTime.now());

        return vacunaCostoRepository.update(existente);
    }

    // Elimina un costo por su ID.
    public void eliminar(Integer id) {
        // Verificamos que existe antes de intentar eliminar
        vacunaCostoRepository.findCostoById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Costo con id " + id + " no encontrado"
                ));

        vacunaCostoRepository.deleteCostoById(id);
    }
}
