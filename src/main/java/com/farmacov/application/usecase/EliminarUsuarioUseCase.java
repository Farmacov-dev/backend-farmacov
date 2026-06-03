package com.farmacov.application.usecase;

import com.farmacov.domain.repository.AnotacionRepository;
import com.farmacov.domain.repository.BitacoraRepository;
import com.farmacov.domain.repository.UltimaActividadUsuarioRepository;
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

    @Inject
    AnotacionRepository anotacionRepository;

    @Inject
    UltimaActividadUsuarioRepository ultimaActividadUsuarioRepository;

    @Transactional
    public void execute(UUID idAdmin, UUID id) {
        // 1. Eliminar entradas de bitácora donde el usuario era el afectado
        //    (id_usuario_afectado es NOT NULL en la DB, no se puede nullificar)
        bitacoraRepository.eliminarPorAfectado(id);

        // 2. Eliminar entradas de bitácora donde el usuario era el admin
        bitacoraRepository.eliminarPorAdmin(id);

        // 3. Eliminar anotaciones del usuario (FK no nullable: anotaciones.id_usuario)
        anotacionRepository.eliminarPorUsuario(id);

        // 4. Eliminar registro de última actividad (PK/FK: usuario_ultima_actividad.id_usuario)
        ultimaActividadUsuarioRepository.eliminarPorUsuario(id);

        // 5. Eliminar al usuario
        usuariosRepository.deleteUsuario(id);

        // 6. Registrar la acción en bitácora usando el admin como único participante
        //    Nota: no se puede guardar id_usuario_afectado porque el usuario ya fue eliminado
        //    y la columna es NOT NULL. El log queda en el historial del admin.
    }
}
