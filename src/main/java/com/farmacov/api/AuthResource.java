package com.farmacov.api;

import com.farmacov.application.dto.RegistroDto;
import com.farmacov.application.dto.UsuarioResponseDto;
import com.farmacov.application.usecase.LoginUseCase;
import com.farmacov.application.usecase.RegistroUseCase;
import com.farmacov.domain.auth.IdentityProvider;
import com.farmacov.domain.models.Usuarios;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.validation.Valid;

import java.util.UUID;

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
    @Inject
    IdentityProvider identityProvider;
    @Inject
    UsuariosRepository usuariosRepository;

    // POST /auth/login
    // El frontend manda el JWT en el header Authorization
    // Verificamos con Firebase y devolvemos datos del usuario de MySQL
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Login de usuario",
            description = "Recibe el JWT del proveedor de autenticación en el header Authorization, lo verifica y devuelve los datos del usuario desde MySQL incluyendo rol y permisos"
    )

    @APIResponse(
            responseCode = "200",
            description = "Login exitoso — devuelve datos del usuario",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "usuario autenticado",
                            value = "{\"email\":\"atorres@farmacov.com\",\"nombre\":\"Andres\",\"apellidoPaterno\":\"Torres\",\"apellidoMaterno\":\"\",\"departamento\":\"Dirección General\",\"rol\":\"Director de Análisis \",\"esAdmin\":true,\"permisos\":{\"dashboard\":true,\"catalogo\":true,\"analisis\":true}}"
                    )
            )
    )

    @APIResponse(
            responseCode = "401",
            description = "Token inválido, expirado o usuario inhabilitado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "sin token",
                            value = "{\"error\": \"Token requerido\"}"
                    )
            )
    )



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

    @Operation(
            summary = "Obtener usuario autenticado",
            description = "Devuelve los datos del usuario actualmente autenticado — útil para restaurar la sesión cuando el usuario recarga la página sin necesidad de volver a hacer login"
    )

    @APIResponse(
            responseCode = "200",
            description = "Datos del usuario obtenidos correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "usuario actual",
                            value = "{\"email\":\"atorres@farmacov.com\",\"nombre\":\"Andres\",\"apellidoPaterno\":\"Torres\",\"apellidoMaterno\":\"\",\"departamento\":\"Dirección General\",\"rol\":\"Director de Análisis \",\"esAdmin\":true,\"permisos\":{\"dashboard\":true,\"catalogo\":true,\"analisis\":true}}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Usuario no encontrado en el sistema",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"Usuario no encontrado en el sistema\"}"
                    )
            )
    )

    public Response me(@Context ContainerRequestContext ctx) {
        String firebaseUid = (String) ctx.getProperty("firebase_uid");
        UsuarioResponseDto usuario = loginUseCase.executeFromUid(firebaseUid);
        return Response.ok(usuario).build();
    }


    @POST
    @Path("/registro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Crea un nuevo usuario en el proveedor de autenticación y en MySQL. Solo puede ser ejecutado por un usuario con token válido,  el id del admin se obtiene desde el token"
    )

    @RequestBody(
            description = "Datos del nuevo usuario a registrar",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "registro",
                            value = "{\"nombre\":\"Ana\",\"apellidoPaterno\":\"Martinez\",\"apellidoMaterno\":\"Lopez\",\"correo\":\"amartinez@farmacov.com\",\"password\":\"Secret!23*&\",\"departamento\":\"Análisis\",\"idRol\":1}"
                    )
            )
    )

    @APIResponse(
            responseCode = "201",
            description = "Usuario registrado correctamente",
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
            description = "Token requerido para registrar usuarios",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "sin token",
                            value = "{\"error\": \"Token requerido para registrar usuarios\"}"
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



    public Response registro(@Context HttpHeaders headers, @Valid RegistroDto dto) {
        UUID idAdmin = resolverAdminDesdeToken(headers);
        if (idAdmin == null) {
            return Response.status(401)
                    .entity("{\"error\": \"Token requerido para registrar usuarios\"}")
                    .build();
        }
        UsuarioResponseDto usuario = registroUseCase.execute(dto, idAdmin);
        return Response.status(201).entity(usuario).build();
    }

    /** Lee el Bearer token del header y lo convierte al UUID del usuario en la BD. */
    private UUID resolverAdminDesdeToken(HttpHeaders headers) {
        String authHeader = headers.getHeaderString("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        try {
            String firebaseUid = identityProvider.verifyToken(authHeader.substring(7));
            return usuariosRepository.findUsuarioByFirebaseUuid(firebaseUid)
                    .map(Usuarios::getId)
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}