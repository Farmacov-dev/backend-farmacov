package com.farmacov.application.usecase;

import com.farmacov.application.dto.UsuarioResponseDto;
import com.farmacov.domain.auth.IdentityProvider;
import com.farmacov.domain.models.Usuarios;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class LoginUseCase {

    @Inject
    IdentityProvider identityProvider;

    @Inject
    UsuariosRepository usuariosRepository;

    // Para POST /auth/login, recibe el token y lo verifica
    public UsuarioResponseDto execute(String token) {
        String firebaseUid = identityProvider.verifyToken(token);
        return buscarYMapear(firebaseUid);
    }

    // Para GET /auth/me,e uid ya fue verificado por el AuthFilter
    public UsuarioResponseDto executeFromUid(String firebaseUid) {
        return buscarYMapear(firebaseUid);
    }

    private UsuarioResponseDto buscarYMapear(String firebaseUid) {
        Usuarios usuario = usuariosRepository.findUsuarioByFirebaseUuid(firebaseUid)
                .orElseThrow(() -> new NotFoundException(
                        "Usuario no encontrado en el sistema"
                ));

        return toDto(usuario);
    }

    private UsuarioResponseDto toDto(Usuarios usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setEmail(usuario.getCorreo());
        dto.setNombre(usuario.getNombre());
        dto.setApellidoPaterno(usuario.getApellidoPaterno());
        dto.setApellidoMaterno(
                usuario.getApellidoMaterno() != null ? usuario.getApellidoMaterno() : ""
        );
        dto.setDepartamento(usuario.getDepartamento());
        dto.setRol(usuario.getRol() != null ? usuario.getRol().getNombre() : "");
        return dto;
    }
}