package com.farmacov.api;
import com.farmacov.application.dto.ActualizarUsuarioDto;
import com.farmacov.application.dto.RegistroDto;
import com.farmacov.application.dto.UsuarioResponseDto;
import com.farmacov.application.usecase.ActualizarUsuarioUseCase;
import com.farmacov.application.usecase.EliminarUsuarioUseCase;
import com.farmacov.application.usecase.ObtenerTodosLosUsuarios;
import com.farmacov.application.usecase.RegistroUseCase;
import com.farmacov.domain.models.Usuarios;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/usuarios")
@ApplicationScoped
public class UsuariosResource {

    @Inject
    ObtenerTodosLosUsuarios obtenerTodosLosUsuarios;

    @Inject
    RegistroUseCase registroUseCase;

    @Inject
    ActualizarUsuarioUseCase actualizarUsuarioUseCase;

    @Inject
    EliminarUsuarioUseCase eliminarUsuarioUseCase;

    @Inject
    UsuariosRepository usuariosRepository;

    /** Resuelve el UUID del usuario autenticado a partir del firebase_uid que dejó el AuthFilter */
    private UUID resolverIdAdmin(ContainerRequestContext ctx) {
        String firebaseUid = (String) ctx.getProperty("firebase_uid");
        if (firebaseUid == null) return null;
        return usuariosRepository.findUsuarioByFirebaseUuid(firebaseUid)
                .map(Usuarios::getId)
                .orElse(null);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearUsuario(@Context ContainerRequestContext ctx,
                                 @Valid RegistroDto dto) {
        UUID idAdmin = resolverIdAdmin(ctx);
        if (idAdmin == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Token requerido\"}")
                    .build();
        }
        UsuarioResponseDto creado = registroUseCase.execute(dto, idAdmin);
        return Response.status(201).entity(creado).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Usuarios> usuarios = obtenerTodosLosUsuarios.execute();
        return Response.ok(usuarios).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuario(@Context ContainerRequestContext ctx,
                                      @PathParam("id") UUID id,
                                      @Valid ActualizarUsuarioDto dto) {
        UUID idAdmin = resolverIdAdmin(ctx);
        if (idAdmin == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Token requerido\"}")
                    .build();
        }
        Usuarios actualizado = actualizarUsuarioUseCase.execute(idAdmin, id, dto);
        return Response.ok(actualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarUsuario(@Context ContainerRequestContext ctx,
                                    @PathParam("id") UUID id) {
        UUID idAdmin = resolverIdAdmin(ctx);
        if (idAdmin == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"Token requerido\"}")
                    .build();
        }
        eliminarUsuarioUseCase.execute(idAdmin, id);
        return Response.noContent().build();
    }

}
