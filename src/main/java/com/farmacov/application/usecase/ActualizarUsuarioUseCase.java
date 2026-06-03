package com.farmacov.application.usecase;
import com.farmacov.application.dto.ActualizarUsuarioDto;
import com.farmacov.domain.models.Bitacora;
import com.farmacov.domain.models.Roles;
import com.farmacov.domain.models.Usuarios;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class ActualizarUsuarioUseCase {

    @Inject
    UsuariosRepository usuariosRepository;

    @Inject
    RegistrarBitacoraUseCase registrarBitacoraUseCase;

    @Transactional
    public Usuarios execute(UUID id, ActualizarUsuarioDto dto) {
        // se arma el modelo con los campos a editar
        Usuarios usuario = new Usuarios();
        usuario.setId(id);
        usuario.setDepartamento(dto.getDepartamento());

        // el impl solo ocupa el id del rol para hacer el getref
        Roles rol = new Roles();
        rol.setId(dto.getIdRol());
        usuario.setRol(rol);

        Usuarios actualizado = usuariosRepository.updateUsuario(usuario);

        // registrar la acción en bitácora dentro de la misma transacción
        registrarBitacoraUseCase.execute(dto.getIdAdmin(), Bitacora.AccionEnum.UPDATE, id);

        return actualizado;
    }
}
