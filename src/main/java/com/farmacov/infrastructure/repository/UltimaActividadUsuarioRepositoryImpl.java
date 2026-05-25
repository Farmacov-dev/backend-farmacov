package com.farmacov.infrastructure.repository;

import com.farmacov.domain.models.UltimaActividadUsuario;
import com.farmacov.domain.repository.UltimaActividadUsuarioRepository;
import com.farmacov.infrastructure.entities.UltimaActividadUsuarioEntity;
import com.farmacov.infrastructure.mapper.UltimaActividadUsuarioMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UltimaActividadUsuarioRepositoryImpl
        implements UltimaActividadUsuarioRepository, PanacheRepositoryBase<UltimaActividadUsuarioEntity, UUID> {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public void upsert(UltimaActividadUsuario actividadUsuario) {
        em.createNativeQuery("""
                INSERT INTO usuario_ultima_actividad
                (id_usuario, endpoint, metodo_http, status_code, query_string, user_agent, ip_cliente, creado_en, actualizado_en)
                VALUES
                (:idUsuario, :endpoint, :metodoHttp, :statusCode, :queryString, :userAgent, :ipCliente, NOW(), NOW())
                ON DUPLICATE KEY UPDATE
                    endpoint = VALUES(endpoint),
                    metodo_http = VALUES(metodo_http),
                    status_code = VALUES(status_code),
                    query_string = VALUES(query_string),
                    user_agent = VALUES(user_agent),
                    ip_cliente = VALUES(ip_cliente),
                    actualizado_en = VALUES(actualizado_en)
                """)
                .setParameter("idUsuario", actividadUsuario.getIdUsuario())
                .setParameter("endpoint", actividadUsuario.getEndpoint())
                .setParameter("metodoHttp", actividadUsuario.getMetodoHttp())
                .setParameter("statusCode", actividadUsuario.getStatusCode())
                .setParameter("queryString", actividadUsuario.getQueryString())
                .setParameter("userAgent", actividadUsuario.getUserAgent())
                .setParameter("ipCliente", actividadUsuario.getIpCliente())
                .executeUpdate();
    }

    @Override
    @Transactional
    public Optional<UltimaActividadUsuario> findByUsuarioId(UUID idUsuario) {
        return findByIdOptional(idUsuario).map(UltimaActividadUsuarioMapper::toDomain);
    }
}
