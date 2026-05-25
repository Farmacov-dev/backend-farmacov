package com.farmacov.application.usecase;

import com.farmacov.application.dto.CrearFarmacoDto;
import com.farmacov.domain.models.Farmaco;
import com.farmacov.domain.repository.FarmacoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class FarmacoUseCase {

    @Inject
    FarmacoRepository farmacoRepository;

    public List<Farmaco> obtenerTodos() {
        return farmacoRepository.findAllFarmacos(); // ← nombre corregido
    }

    public Farmaco obtenerPorId(Integer id) {
        return farmacoRepository.findFarmacoById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Farmaco con id " + id + " no encontrado"
                ));
    }

    public Farmaco crear(CrearFarmacoDto dto) {
        Farmaco nuevo = new Farmaco();
        nuevo.setNombre(dto.getNombre());
        nuevo.setTipo(dto.getTipo());
        nuevo.setDescripcion(dto.getDescripcion());
        return farmacoRepository.save(nuevo);
    }

    public Farmaco actualizar(Integer id, CrearFarmacoDto dto) {
        farmacoRepository.findFarmacoById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Farmaco con id " + id + " no encontrado"
                ));

        Farmaco actualizado = new Farmaco();
        actualizado.setId(id);
        actualizado.setNombre(dto.getNombre());
        actualizado.setTipo(dto.getTipo());
        actualizado.setDescripcion(dto.getDescripcion());

        return farmacoRepository.update(actualizado);
    }

    public void eliminar(Integer id) {
        farmacoRepository.findFarmacoById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Farmaco con id " + id + " no encontrado"
                ));

        farmacoRepository.deleteFarmacoById(id);
    }
}