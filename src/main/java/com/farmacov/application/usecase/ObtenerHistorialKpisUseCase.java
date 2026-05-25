package com.farmacov.application.usecase;

import com.farmacov.application.dto.HistorialKpisDto;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ObtenerHistorialKpisUseCase {

    @Inject
    UsuariosRepository usuariosRepository;

    public HistorialKpisDto execute() {
        long usuariosActivos = usuariosRepository.countByEstado("ACTIVO");
        long usuariosSuspendidos = usuariosRepository.countByEstado("SUSPENDIDO");

        return new HistorialKpisDto(usuariosActivos, usuariosSuspendidos);
    }
}
