package com.farmacov.application.usecase;

import com.farmacov.application.dto.ActualizarAnotacionDto;
import com.farmacov.application.dto.CrearAnotacionDto;
import com.farmacov.domain.models.Anotacion;
import com.farmacov.domain.repository.AnotacionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AnotacionUseCase {

    @Inject
    AnotacionRepository anotacionRepository;

    public Anotacion crear(CrearAnotacionDto dto) {
        Anotacion anotacion = new Anotacion();
        anotacion.setIdUsuario(dto.getIdUsuario());
        anotacion.setDashboardReferencia(dto.getDashboardReferencia());
        anotacion.setTitulo(dto.getTitulo());
        anotacion.setObservaciones(dto.getObservaciones());
        return anotacionRepository.save(anotacion);
    }

    public Anotacion actualizar(Integer id, ActualizarAnotacionDto dto) {
        Anotacion anotacion = new Anotacion();
        anotacion.setId(id);
        anotacion.setIdUsuario(dto.getIdUsuario());
        anotacion.setDashboardReferencia(dto.getDashboardReferencia());
        anotacion.setTitulo(dto.getTitulo());
        anotacion.setObservaciones(dto.getObservaciones());
        return anotacionRepository.update(anotacion);
    }

    public Anotacion obtenerPorId(Integer id) {
        return anotacionRepository.findAnotacionById(id)
                .orElseThrow(() -> new jakarta.ws.rs.NotFoundException(
                        "Anotacion con id " + id + " no encontrada"
                ));
    }

    public List<Anotacion> listarTodas() {
        return anotacionRepository.findAllAnotaciones();
    }

    public List<Anotacion> listarPorUsuario(UUID idUsuario) {
        return anotacionRepository.findByUsuarioId(idUsuario);
    }

    public void eliminar(Integer id) {
        anotacionRepository.deleteAnotacionById(id);
    }
}
