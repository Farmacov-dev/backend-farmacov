package com.farmacov.api;

import com.farmacov.application.dto.RegistroDto;
import com.farmacov.application.dto.UsuarioResponseDto;
import com.farmacov.application.usecase.LoginUseCase;
import com.farmacov.application.usecase.RegistroUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.validation.Valid;

///  imports de documentacio
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@Path("/auth")
@Tag(name = "Autenticacion", description = "autenticacion y registro de usuario via un metodo externo (Ej. Firebase, auth0")
@ApplicationScoped
public class AuthResource {

    @Inject
    LoginUseCase loginUseCase;
    @Inject
    RegistroUseCase registroUseCase;

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
    // si el usuario hace refresh
    // se guarda el uuid en el contexto
    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public Response me(@Context ContainerRequestContext ctx) {
        String firebaseUid = (String) ctx.getProperty("firebase_uid");
        UsuarioResponseDto usuario = loginUseCase.executeFromUid(firebaseUid);
        return Response.ok(usuario).build();
    }


    @POST
    @Path("/registro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registro(@Valid RegistroDto dto) {
        // idAdmin es null porque /auth/registro es una ruta pública sin token.
        // RegistroUseCase usa el UUID del propio usuario creado como actor del log.
        UsuarioResponseDto usuario = registroUseCase.execute(dto, null);
        return Response.status(201).entity(usuario).build();
    }
}