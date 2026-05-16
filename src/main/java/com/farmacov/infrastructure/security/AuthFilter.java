package com.farmacov.infrastructure.security;

import com.farmacov.domain.auth.IdentityProvider;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.util.Set;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    // aqi van las rutas que no requieren token
    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/status",
            "/auth/login"
    );

    @Inject
    IdentityProvider identityProvider;

    @Override
    public void filter(ContainerRequestContext ctx) {
        String path = ctx.getUriInfo().getPath();

        // Si es ruta publica, dejamos pasar sin verificar
        if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
            return;
        }

        String header = ctx.getHeaderString("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            ctx.abortWith(Response.status(401)
                    .entity("{\"error\": \"Token requerido\"}")
                    .build());
            return;
        }

        try {
            // Verificamos el token y guardamos el uuid en el contexto, para que se pueda usar el usecase
            String uid = identityProvider.verifyToken(header.substring(7));
            ctx.setProperty("firebase_uid", uid);
        } catch (Exception e) {
            ctx.abortWith(Response.status(401)
                    .entity("{\"error\": \"Token inválido o usuario inhabilitado\"}")
                    .build());
        }
    }
}