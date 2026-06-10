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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/usuarios")
@ApplicationScoped
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema , todos los endpoints requieren token válido")

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

    @Operation(
            summary = "Crear usuario",
            description = "Registra un nuevo usuario en el proveedor de autenticación y en MySQL. " +
                    "El id del admin que registra se resuelve automáticamente desde el token. " +
                    "apellidoMaterno y departamento son opcionales"
    )

    @APIResponse(
            responseCode = "201",
            description = "Usuario creado correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "usuario creado",
                            value = "{\"email\":\"amartinez@farmacov.com\",\"nombre\":\"Ana\",\"apellidoPaterno\":\"Martinez\",\"apellidoMaterno\":\"Lopez\",\"departamento\":\"Análisis\",\"rol\":\"Director de Análisis Farmacéutico\",\"esAdmin\":false,\"permisos\":{\"dashboard\":true,\"catalogo\":true,\"analisis\":true}}"
                    )
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "Datos inválidos — campos requeridos faltantes o formato incorrecto",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "error validacion",
                            value = "{\"error\": \"El correo no tiene formato válido\"}"
                    )
            )
    )

    @APIResponse(
            responseCode = "401",
            description = "Token requerido",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "sin token",
                            value = "{\"error\": \"Token requerido\"}"
                    )
            )
    )

    @APIResponse(
            responseCode = "409",
            description = "El correo ya está registrado en el proveedor de autenticación",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "email duplicado",
                            value = "{\"error\": \"El correo ya está registrado\"}"
                    )
            )
    )


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

    // GET /usuarios
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Listar todos los usuarios",
            description = "Devuelve todos los usuarios registrados en el sistema con sus datos y rol asignado"
    )

    @APIResponse(
            responseCode = "200",
            description = "Lista de usuarios obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "lista usuarios",
                            value = "[{\"email\":\"jrodriguez@farmacov.com\",\"nombre\":\"Jose\",\"apellidoPaterno\":\"Rodriguez\",\"apellidoMaterno\":\"\",\"departamento\":\"Dirección General\",\"rol\":\"Director de Análisis Farmacéutico\",\"esAdmin\":true,\"permisos\":{\"dashboard\":true,\"catalogo\":true,\"analisis\":true}}]"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    public Response getAll() {
        List<Usuarios> usuarios = obtenerTodosLosUsuarios.execute();
        return Response.ok(usuarios).build();
    }

    // PUT /usuarios/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza el departamento y rol de un usuario existente. " +
                    "El id del admin que realiza el cambio se resuelve desde el token"
    )

    @Parameter(name = "id", description = "UUID del usuario a actualizar", example = "550e8400-e29b-41d4-a716-446655440000")

    @APIResponse(
            responseCode = "200",
            description = "Usuario actualizado correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "usuario actualizado",
                            value = "{\"email\":\"jrodriguez@farmacov.com\",\"nombre\":\"Jose\",\"apellidoPaterno\":\"Rodriguez\",\"apellidoMaterno\":\"\",\"departamento\":\"Análisis Avanzado\",\"rol\":\"Chalan\",\"esAdmin\":false,\"permisos\":{\"dashboard\":true,\"catalogo\":false,\"analisis\":false}}"
                    )
            )
    )

    @APIResponse(
            responseCode = "401",
            description = "Token requerido",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "sin token",
                            value = "{\"error\": \"Token requerido\"}"
                    )
            )
    )

    @APIResponse(
            responseCode = "404",
            description = "Usuario no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"Usuario no encontrado\"}"
                    )
            )
    )

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

    // DELETE /usuarios{id}
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario del sistema y del proveedor de autenticación. " +
                    "El id del admin que realiza la eliminación se resuelve desde el token"
    )

    @Parameter(name = "id", description = "UUID del usuario a eliminar", example = "550e8400-e29b-41d4-a716-446655440000")

    @APIResponse(responseCode = "204", description = "Usuario eliminado correctamente — sin contenido")

    @APIResponse(
            responseCode = "401",
            description = "Token requerido",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "sin token",
                            value = "{\"error\": \"Token requerido\"}"
                    )
            )
    )

    @APIResponse(
            responseCode = "404",
            description = "Usuario no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"Usuario no encontrado\"}"
                    )
            )
    )

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
