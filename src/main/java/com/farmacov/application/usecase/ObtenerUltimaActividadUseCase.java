package com.farmacov.application.usecase;

import com.farmacov.application.dto.UltimaActividadUsuarioResponseDto;
import com.farmacov.domain.models.UltimaActividadUsuario;
import com.farmacov.domain.models.Usuarios;
import com.farmacov.domain.repository.UltimaActividadUsuarioRepository;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class ObtenerUltimaActividadUseCase {

    @Inject
    UsuariosRepository usuariosRepository;

    @Inject
    UltimaActividadUsuarioRepository ultimaActividadUsuarioRepository;

    public Optional<UltimaActividadUsuarioResponseDto> execute(String firebaseUid) {
        if (firebaseUid == null || firebaseUid.isBlank()) {
            return Optional.empty();
        }

        Usuarios usuario = usuariosRepository.findUsuarioByFirebaseUuid(firebaseUid).orElse(null);
        if (usuario == null || usuario.getId() == null) {
            return Optional.empty();
        }

        return ultimaActividadUsuarioRepository.findByUsuarioId(usuario.getId())
                .map(this::toDto);
    }

    private UltimaActividadUsuarioResponseDto toDto(UltimaActividadUsuario actividad) {
        UltimaActividadUsuarioResponseDto dto = new UltimaActividadUsuarioResponseDto();
        dto.setIdUsuario(actividad.getIdUsuario());
        dto.setEndpoint(actividad.getEndpoint());
        dto.setMetodoHttp(actividad.getMetodoHttp());
        dto.setStatusCode(actividad.getStatusCode());
        dto.setQueryString(actividad.getQueryString());
        dto.setUserAgent(actividad.getUserAgent());
        dto.setIpCliente(actividad.getIpCliente());
        dto.setCreadoEn(actividad.getCreadoEn());
        dto.setActualizadoEn(actividad.getActualizadoEn());
        return dto;
    }
}
