package com.farmacov.api;

import com.farmacov.application.dto.UsuarioResponseDto;
import com.farmacov.application.usecase.LoginUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@ApplicationScoped
public class AuthResource {

    @Inject
    LoginUseCase loginUseCase;

    // POST /auth/login
    // El frontend manda el JWT en el header Authorization
    // Verificamos con Firebase y devolvemos datos del usuario de MySQL
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context HttpHeaders headers) {
        String authHeader = headers.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(401)
                    .entity("{\"error\": \"Token requerido\"}")
                    .build();
        }

        String token = authHeader.substring(7);
        UsuarioResponseDto usuario = loginUseCase.execute(token);
        return Response.ok(usuario).build();
    }

    // GET /auth/me
    // si el usuario hace refersh
    // se guarda el uuid en el contexto
    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public Response me(@Context ContainerRequestContext ctx) {
        String firebaseUid = (String) ctx.getProperty("firebase_uid");
        UsuarioResponseDto usuario = loginUseCase.executeFromUid(firebaseUid);
        return Response.ok(usuario).build();
    }
}