package com.farmacov.application.usecase;

import com.farmacov.domain.repository.BitacoraRepository;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class EliminarUsuarioUseCase {

    @Inject
    UsuariosRepository usuariosRepository;

    @Inject
    BitacoraRepository bitacoraRepository;

    @Transactional
    public void execute(UUID id) {
        bitacoraRepository.eliminarPorUsuario(id);
        usuariosRepository.deleteUsuario(id);
    }
}
