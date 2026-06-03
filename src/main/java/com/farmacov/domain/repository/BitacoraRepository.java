package com.farmacov.domain.repository;

import com.farmacov.domain.models.Bitacora;

import java.util.List;
import java.util.UUID;

public interface BitacoraRepository {

    void registrar(Bitacora bitacora);

    List<Bitacora> obtenerTodos();

    // elimina entradas donde el usuario era el admin (necesario antes de borrar al usuario)
    void eliminarPorAdmin(UUID idAdmin);

    // elimina entradas donde el usuario era el afectado (id_usuario_afectado NOT NULL en DB, no se puede nullificar)
    void eliminarPorAfectado(UUID idUsuario);
}
