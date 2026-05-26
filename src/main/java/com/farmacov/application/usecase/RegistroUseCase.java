package com.farmacov.application.usecase;

import com.farmacov.application.dto.RegistroDto;
import com.farmacov.application.dto.UsuarioResponseDto;
import com.farmacov.domain.auth.IdentityProvider;
import com.farmacov.domain.models.Bitacora;
import com.farmacov.domain.models.Roles;
import com.farmacov.domain.models.Usuarios;
import com.farmacov.domain.repository.RolesRepository;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class RegistroUseCase {

    @Inject
    IdentityProvider identityProvider;

    @Inject
    UsuariosRepository usuariosRepository;

    @Inject
    RolesRepository rolesRepository;

    @Inject
    RegistrarBitacoraUseCase registrarBitacoraUseCase;

    @Transactional
    public UsuarioResponseDto execute(RegistroDto dto, UUID idAdmin) {
        // 1. Verificamos que el rol existe antes de hacer nada
        Roles rol = rolesRepository.findRoleById(dto.getIdRol())
                .orElseThrow(() -> new NotFoundException(
                        "Rol con id " + dto.getIdRol() + " no encontrado"
                ));

        // 2. Creamos el usuario en Firebase,nos devuelve el uuid
        String firebaseUuid = identityProvider.crearUsuario(
                dto.getCorreo(),
                dto.getPassword()
        );

        // 3. Guardamos en MySQL con el firebaseUuid que nos dio Firebase
        Usuarios nuevo = new Usuarios();
        nuevo.setId(UUID.randomUUID()); // id del sistema
        nuevo.setFirebaseUuid(firebaseUuid); // id de firebase
        nuevo.setNombre(dto.getNombre());
        nuevo.setApellidoPaterno(dto.getApellidoPaterno());
        nuevo.setApellidoMaterno(dto.getApellidoMaterno());
        nuevo.setCorreo(dto.getCorreo());
        nuevo.setDepartamento(dto.getDepartamento());
        nuevo.setRol(rol);
        nuevo.setCreadoEn(LocalDateTime.now());
        nuevo.setActualizadoEn(LocalDateTime.now());

        Usuarios guardado = usuariosRepository.saveUsuario(nuevo);

        UUID actorId = (idAdmin != null) ? idAdmin : guardado.getId();
        registrarBitacoraUseCase.execute(actorId, Bitacora.AccionEnum.CREATE, guardado.getId());

        UsuarioResponseDto response = new UsuarioResponseDto();
        response.setEmail(guardado.getCorreo());
        response.setNombre(guardado.getNombre());
        response.setApellidoPaterno(guardado.getApellidoPaterno());
        response.setApellidoMaterno(
                guardado.getApellidoMaterno() != null ? guardado.getApellidoMaterno() : ""
        );
        response.setDepartamento(guardado.getDepartamento());
        response.setRol(rol.getNombre());

        return response;
    }
}