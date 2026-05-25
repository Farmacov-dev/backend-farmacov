/*
package com.farmacov.infrastructure.filter;

import com.farmacov.application.usecase.ActualizarUltimaActividadUseCase;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.USER)
public class UltimaActividadFilter implements ContainerResponseFilter {

    private static final String ACTIVIDAD_PATH_PREFIX = "actividad";

    @Inject
    ActualizarUltimaActividadUseCase actualizarUltimaActividadUseCase;

    @Override
    @Transactional
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String path = requestContext.getUriInfo().getPath();

        if (path == null || path.startsWith(ACTIVIDAD_PATH_PREFIX)) {
            return;
        }

        String firebaseUid = (String) requestContext.getProperty("firebase_uid");
        if (firebaseUid == null || firebaseUid.isBlank()) {
            return;
        }

        String metodoHttp = requestContext.getMethod();
        if (metodoHttp == null || metodoHttp.equalsIgnoreCase("OPTIONS")) {
            return;
        }

        String endpoint = normalizeEndpoint(path);
        Integer statusCode = responseContext.getStatus();
        String queryString = requestContext.getUriInfo().getRequestUri().getRawQuery();
        String userAgent = requestContext.getHeaderString("User-Agent");
        String ipCliente = requestContext.getHeaderString("X-Forwarded-For");

        actualizarUltimaActividadUseCase.execute(
                firebaseUid,
                endpoint,
                metodoHttp,
                statusCode,
                queryString,
                userAgent,
                ipCliente
        );
    }

    private String normalizeEndpoint(String path) {
        if (path.startsWith("/")) {
            return path;
        }
        return "/" + path;
    }
}
*/
