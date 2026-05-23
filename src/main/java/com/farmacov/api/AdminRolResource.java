package com.farmacov.api;

import com.farmacov.domain.models.Roles;
import com.farmacov.domain.repository.RolesRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

// imports de documentacion

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

//

@Path("/admin/roles")
@Tag(name = "Admin - Roles", description = "Gestion de roles y permisos del sistema")
public class AdminRolResource {

    @Inject
    RolesRepository rolesRepository;

    // GET /admin/roles — lista todos los roles con sus permisos
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "listar todos los roles",
            description = "regresa los roles con los permisos para ver cada pagina "
    )

    @APIResponse(
            responseCode = "200",
            description = "lista de roles obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "roles",
                            value = "[{\"id\":1,\"nombre\":\"Administrador\",\"esAdmin\":true,\"permisos\":{\"dashboard\":true,\"catalogo\":true,\"analisis\":true}},{\"id\":2,\"nombre\":\"Analista\",\"esAdmin\":false,\"permisos\":{\"dashboard\":true,\"catalogo\":false,\"analisis\":true}}]"
                    )

            )

    )

    @APIResponse(responseCode = "401", description = "No autorizado")

    public Response getRoles() {
        List<Roles> roles = rolesRepository.findAllRoles();
        return Response.ok(roles).build();
    }

    // PUT /admin/roles/{idRol}/permisos — actualiza permisos de un rol
    @PUT
    @Path("/{idRol}/permisos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(
            summary = "actualizar permisos de un rol",
            description = "modifica los permisos asociados a un rol especifico"
    )

    @APIResponse(
            responseCode = "200",
            description = "permisos actualizados correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "rol actualizado",
                            value = "{\"id\":1,\"nombre\":\"Director de Análisis Farmacéutico\",\"esAdmin\":true,\"permisos\":{\"dashboard\":true,\"catalogo\":true,\"analisis\":false}}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description =  "no autorizado")
    @APIResponse(responseCode = "404", description =  "rol no encontrado")

    public Response updatePermisos(
            @PathParam("idRol") Integer idRol,
            Map<String, Boolean> permisos
    ) {
        Roles rol = rolesRepository.findRoleById(idRol)
                .orElseThrow(() -> new NotFoundException(
                        "Rol con id " + idRol + " no encontrado"
                ));

        rol.setPermisos(permisos);
        rolesRepository.updateRole(rol);
        return Response.ok(rol).build();
    }
}