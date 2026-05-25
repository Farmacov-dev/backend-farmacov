package com.farmacov.application.usecase;

import com.farmacov.application.dto.BitacoraResponseDto;
import com.farmacov.domain.models.Bitacora;
import com.farmacov.domain.models.Usuarios;
import com.farmacov.domain.repository.BitacoraRepository;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ObtenerBitacoraUseCase {

    @Inject
    BitacoraRepository bitacoraRepository;

    @Inject
    UsuariosRepository usuariosRepository;

    public List<BitacoraResponseDto> execute() {
        return bitacoraRepository.obtenerTodos()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private BitacoraResponseDto toDto(Bitacora bitacora) {
        String nombreAdmin = resolverNombre(bitacora);
        return new BitacoraResponseDto(
                nombreAdmin,
                bitacora.getAccion().name(),
                bitacora.getCreadoEn()
        );
    }

    private String resolverNombre(Bitacora bitacora) {
        Optional<Usuarios> admin = usuariosRepository.findUsuarioById(bitacora.getIdAdmin());
        return admin
                .map(u -> u.getNombre() + " " + u.getApellidoPaterno())
                .orElse("Usuario desconocido");
    }
}
