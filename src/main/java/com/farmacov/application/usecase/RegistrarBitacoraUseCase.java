package com.farmacov.application.usecase;

import com.farmacov.domain.models.Bitacora;
import com.farmacov.domain.repository.BitacoraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class RegistrarBitacoraUseCase {

    @Inject
    BitacoraRepository bitacoraRepository;

    @Transactional
    public void execute(UUID idAdmin, Bitacora.AccionEnum accion, UUID idUsuarioAfectado) {
        Bitacora entrada = new Bitacora(idAdmin, accion, idUsuarioAfectado, LocalDateTime.now());
        bitacoraRepository.registrar(entrada);
    }
}
