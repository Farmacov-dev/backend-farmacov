package com.farmacov.application.usecase;

import com.farmacov.application.dto.ActualizarSintomaGraveDto;
import com.farmacov.application.dto.CrearSintomaGraveDto;
import com.farmacov.domain.models.SintomaGrave;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import com.farmacov.domain.repository.SintomaGraveRepository;
import com.farmacov.domain.repository.VacunaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class SintomaGraveUseCase {

    @Inject
    SintomaGraveRepository sintomaGraveRepository;

    @Inject
    VacunaRepository vacunaRepository;

    @Inject
    ReporteAdversoRepository reporteAdversoRepository;

    // Crea un síntoma grave nuevo asociado a una vacuna
    public SintomaGrave crear(CrearSintomaGraveDto dto) {
        // Validamos que la vacuna existe
        vacunaRepository.findVacunaById(dto.getIdVacuna())
                .orElseThrow(() -> new NotFoundException(
                        "Vacuna con id " + dto.getIdVacuna() + " no encontrada"
                ));

        SintomaGrave nuevo = new SintomaGrave();
        nuevo.setIdVacuna(dto.getIdVacuna());
        nuevo.setNombre(dto.getNombre());

        return sintomaGraveRepository.save(nuevo);
    }

    // Obtiene todos los síntomas de una vacuna
    public List<SintomaGrave> obtenerPorVacuna(Integer idVacuna) {
        vacunaRepository.findVacunaById(idVacuna)
                .orElseThrow(() -> new NotFoundException(
                        "Vacuna con id " + idVacuna + " no encontrada"
                ));

        return sintomaGraveRepository.findByIdVacuna(idVacuna);
    }

    // Obtiene todos los síntomas
    public List<SintomaGrave> obtenerTodos() {
        return sintomaGraveRepository.getAll();
    }

    // Busca un síntoma por su ID
    public SintomaGrave obtenerPorId(Integer id) {
        return sintomaGraveRepository.getById(id)
                .orElseThrow(() -> new NotFoundException(
                        "SintomaGrave con id " + id + " no encontrado"
                ));
    }

    // Actualiza el nombre de un síntoma existente
    public SintomaGrave actualizar(Integer id, ActualizarSintomaGraveDto dto) {
        sintomaGraveRepository.getById(id)
                .orElseThrow(() -> new NotFoundException(
                        "SintomaGrave con id " + id + " no encontrado"
                ));

        SintomaGrave actualizado = new SintomaGrave();
        actualizado.setId(id);
        actualizado.setNombre(dto.getNombre());

        return sintomaGraveRepository.update(actualizado);
    }

    // Elimina un síntoma solo si no tiene reportes adversos
    public void eliminar(Integer id) {
        sintomaGraveRepository.getById(id)
                .orElseThrow(() -> new NotFoundException(
                        "SintomaGrave con id " + id + " no encontrado"
                ));

        long reportes = reporteAdversoRepository.countByIdSintoma(id);
        if (reportes > 0) {
            throw new BadRequestException(
                    "No se puede eliminar — tiene " + reportes + " reporte(s) adverso(s) asociado(s)"
            );
        }

        sintomaGraveRepository.deleteSintomaById(id);
    }
}