//quitar comentado cuando VacunaRepositoryImpl exista y se confirme
/*package com.farmacov.application.usecase;

import com.farmacov.application.dto.ActualizarEfectoSecundarioDto;
import com.farmacov.application.dto.CrearEfectoSecundarioDto;
import com.farmacov.domain.models.EfectoSecundario;
import com.farmacov.domain.models.EfectoSecundario.Severidad;
import com.farmacov.domain.repository.EfectoSecundarioRepository;
import com.farmacov.domain.repository.VacunaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class EfectoSecundarioUseCase {

    @Inject
    EfectoSecundarioRepository efectoSecundarioRepository;

    @Inject
    VacunaRepository vacunaRepository;

    public EfectoSecundario crear(CrearEfectoSecundarioDto dto) {
        vacunaRepository.findVacunaById(dto.getIdVacuna())
                .orElseThrow(() -> new NotFoundException(
                        "Vacuna con id " + dto.getIdVacuna() + " no encontrada"
                ));

        EfectoSecundario nuevo = new EfectoSecundario();
        nuevo.setIdVacuna(dto.getIdVacuna());
        nuevo.setDescripcion(dto.getDescripcion());
        //aqui hubo en cambio que hace que dto y dominio  usen el mismo enum sin traduccion
        nuevo.setSeveridad(dto.getSeveridad());

        return efectoSecundarioRepository.save(nuevo);
    }

    public List<EfectoSecundario> obtenerPorVacuna(Integer idVacuna) {
        vacunaRepository.findVacunaById(idVacuna)
                .orElseThrow(() -> new NotFoundException(
                        "Vacuna con id " + idVacuna + " no encontrada"
                ));

        return efectoSecundarioRepository.findByIdVacuna(idVacuna);
    }

    public List<EfectoSecundario> obtenerPorVacunaYSeveridad(Integer idVacuna, Severidad severidad) {
        vacunaRepository.findVacunaById(idVacuna)
                .orElseThrow(() -> new NotFoundException(
                        "Vacuna con id " + idVacuna + " no encontrada"
                ));

        return efectoSecundarioRepository.findByIdVacunaAndSeveridad(idVacuna, severidad);
    }

    public EfectoSecundario obtenerPorId(Integer id) {
        return efectoSecundarioRepository.findEfectoById(id)
                .orElseThrow(() -> new NotFoundException(
                        "EfectoSecundario con id " + id + " no encontrado"
                ));
    }

    public EfectoSecundario actualizar(Integer id, ActualizarEfectoSecundarioDto dto) {
        EfectoSecundario existente = efectoSecundarioRepository.findEfectoById(id)
                .orElseThrow(() -> new NotFoundException(
                        "EfectoSecundario con id " + id + " no encontrado"
                ));

        existente.setDescripcion(dto.getDescripcion());
        // Sin traduccion,  mismo enum directo
        existente.setSeveridad(dto.getSeveridad());

        return efectoSecundarioRepository.update(existente);
    }

    public void eliminar(Integer id) {
        efectoSecundarioRepository.findEfectoById(id)
                .orElseThrow(() -> new NotFoundException(
                        "EfectoSecundario con id " + id + " no encontrado"
                ));

        efectoSecundarioRepository.deleteEfectoById(id);
    }
} */