package com.farmacov.application.usecase;

import com.farmacov.domain.models.UltimaActividadUsuario;
import com.farmacov.domain.models.Usuarios;
import com.farmacov.domain.repository.UltimaActividadUsuarioRepository;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ActualizarUltimaActividadUseCase {

    @Inject
    UsuariosRepository usuariosRepository;

    @Inject
    UltimaActividadUsuarioRepository ultimaActividadUsuarioRepository;

    public void execute(String firebaseUid,
                        String endpoint,
                        String metodoHttp,
                        Integer statusCode,
                        String queryString,
                        String userAgent,
                        String ipCliente) {
        if (firebaseUid == null || firebaseUid.isBlank()) {
            return;
        }

        Usuarios usuario = usuariosRepository.findUsuarioByFirebaseUuid(firebaseUid).orElse(null);
        if (usuario == null || usuario.getId() == null) {
            return;
        }

        UltimaActividadUsuario actividad = new UltimaActividadUsuario();
        actividad.setIdUsuario(usuario.getId());
        actividad.setEndpoint(endpoint);
        actividad.setMetodoHttp(metodoHttp);
        actividad.setStatusCode(statusCode);
        actividad.setQueryString(queryString);
        actividad.setUserAgent(userAgent);
        actividad.setIpCliente(ipCliente);

        ultimaActividadUsuarioRepository.upsert(actividad);
    }
}
