package com.farmacov.infrastructure.security;

import com.farmacov.domain.auth.IdentityProvider;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import com.farmacov.domain.models.Usuarios;
import com.farmacov.domain.repository.UsuariosRepository;


import java.util.Set;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    // aqi van las rutas que no requieren token
    //auth/registro es temporal aqui para pruebas
    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/status",
            "/auth/login",
            "/auth/registro",
            "/dashboard/indice-seguridad",
            "/dashboard/resumen-sintomas",
            "/usuarios",
            "/dashboard/costos",
            "/admin/",
            "/admin/roles/",
            "/auth/me",
            "/dashboard/kpis"
    );

    // adicion para rutas que solo admins pueden usar
    private static final Set<String> ADMIN_PATHS = Set.of(
           // "/admin/"

    );

    @Inject
    IdentityProvider identityProvider;

    @Inject
    UsuariosRepository usuariosRepository;

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

            // query extra si la ruta es de admin
            if (ADMIN_PATHS.stream().anyMatch(path::startsWith)) {
                Usuarios usuario = usuariosRepository
                        .findUsuarioByFirebaseUuid(uid)
                        .orElse(null);

                boolean esAdmin = usuario != null
                        && usuario.getRol() != null
                        && Boolean.TRUE.equals(usuario.getRol().getEsAdmin());

                if (!esAdmin) {
                    ctx.abortWith(Response.status(403)
                            .entity("{\"error\": \" acceso denegado\"}")
                            .build());
                }
            }


        } catch (Exception e) {
            ctx.abortWith(Response.status(401)
                    .entity("{\"error\": \"Token inválido o usuario inhabilitado\"}")
                    .build());
        }
    }
}