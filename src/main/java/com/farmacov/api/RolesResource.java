package com.farmacov.api;

import com.farmacov.application.usecase.ObtenerTodosLosRoles;
import com.farmacov.domain.models.Roles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


import java.util.List;

@Path("/roles")
@ApplicationScoped
@Tag(name = "Roles", description = "Consulta pública de roles disponibles en el sistema")

public class RolesResource {

    @Inject
    ObtenerTodosLosRoles obtenerTodosLosRoles; // inyecta el use case

    @GET
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Listar todos los roles",
            description = "Devuelve todos los roles disponibles en el sistema con sus permisos — " +
                    "usado para poblar el selector de rol en el formulario de registro de usuarios"
    )

    @APIResponse(
            responseCode = "200",
            description = "Lista de roles obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "roles",
                            value = "[{\"id\":1,\"nombre\":\"Director de Análisis Farmacéutico\",\"esAdmin\":true,\"permisos\":{\"dashboard\":true,\"catalogo\":true,\"analisis\":true}},{\"id\":2,\"nombre\":\"Chalan\",\"esAdmin\":false,\"permisos\":{\"dashboard\":true,\"catalogo\":false,\"analisis\":false}}]"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")


    public Response getAll() {
        List<Roles> roles = obtenerTodosLosRoles.execute(); // llama al use case que llama al repo
        return Response.ok(roles).build(); // devuelve la lista en json
    }
}
