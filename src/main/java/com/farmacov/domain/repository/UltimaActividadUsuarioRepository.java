package com.farmacov.domain.repository;

import com.farmacov.domain.models.UltimaActividadUsuario;

import java.util.Optional;
import java.util.UUID;

public interface UltimaActividadUsuarioRepository {

    void upsert(UltimaActividadUsuario actividadUsuario);

    Optional<UltimaActividadUsuario> findByUsuarioId(UUID idUsuario);
}
