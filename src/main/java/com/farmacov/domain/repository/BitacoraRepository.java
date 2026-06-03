package com.farmacov.domain.repository;

import com.farmacov.domain.models.Bitacora;

import java.util.List;
import java.util.UUID;

public interface BitacoraRepository {

    void registrar(Bitacora bitacora);

    List<Bitacora> obtenerTodos();

    void eliminarPorUsuario(UUID idUsuario);
}
