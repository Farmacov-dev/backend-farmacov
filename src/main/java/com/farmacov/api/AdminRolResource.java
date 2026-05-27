package com.farmacov.api;

import com.farmacov.application.dto.CrearRolDto;
import com.farmacov.domain.models.Roles;
import com.farmacov.domain.repository.RolesRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/admin/roles")
@Tag(name = "Admin - Roles", description = "Gestion de roles y permisos del sistema")
public class AdminRolResource {

    @Inject
    RolesRepository rolesRepository;

    // GET /admin/roles
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar todos los roles",
            description = "Regresa los roles con los permisos para ver cada pagina")
    @APIResponse(responseCode = "200", description = "Lista de roles obtenida correctamente")
    @APIResponse(responseCode = "401", description = "No autorizado")
    public Response getRoles() {
        List<Roles> roles = rolesRepository.findAllRoles();
        return Response.ok(roles).build();
    }

    // POST /admin/roles — crear rol nuevo
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear un rol nuevo",
            description = "Crea un rol con nombre, esAdmin y permisos iniciales")
    @APIResponse(responseCode = "201", description = "Rol creado correctamente")
    @APIResponse(responseCode = "400", description = "Datos inválidos")
    @APIResponse(responseCode = "401", description = "No autorizado")
    public Response crearRol(@Valid CrearRolDto dto) {
        Roles nuevo = new Roles();
        nuevo.setNombre(dto.getNombre());
        nuevo.setEsAdmin(dto.getEsAdmin());

        // Si no mandan permisos, inicializamos todos en false
        Map<String, Boolean> permisos = dto.getPermisos() != null
                ? dto.getPermisos()
                : new HashMap<>(Map.of(
                "dashboard", false,
                "catalogo", false,
                "analisis", false
        ));
        nuevo.setPermisos(permisos);

        Roles guardado = rolesRepository.saveRole(nuevo);
        return Response.status(201).entity(guardado).build();
    }

    // PUT /admin/roles/{idRol} — editar nombre y esAdmin
    @PUT
    @Path("/{idRol}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Editar un rol",
            description = "Actualiza nombre y esAdmin de un rol existente")
    @APIResponse(responseCode = "200", description = "Rol actualizado correctamente")
    @APIResponse(responseCode = "404", description = "Rol no encontrado")
    @APIResponse(responseCode = "401", description = "No autorizado")
    public Response editarRol(
            @PathParam("idRol") Integer idRol,
            @Valid CrearRolDto dto
    ) {
        Roles rol = rolesRepository.findRoleById(idRol)
                .orElseThrow(() -> new NotFoundException(
                        "Rol con id " + idRol + " no encontrado"
                ));

        rol.setNombre(dto.getNombre());
        rol.setEsAdmin(dto.getEsAdmin());

        // Solo actualizamos permisos si vienen en el body
        if (dto.getPermisos() != null) {
            rol.setPermisos(dto.getPermisos());
        }

        Roles actualizado = rolesRepository.updateRole(rol);
        return Response.ok(actualizado).build();
    }

    // PUT /admin/roles/{idRol}/permisos — actualiza solo permisos
    @PUT
    @Path("/{idRol}/permisos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Actualizar permisos de un rol",
            description = "Modifica los permisos asociados a un rol especifico")
    @APIResponse(responseCode = "200", description = "Permisos actualizados correctamente",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "rol actualizado",
                            value = "{\"id\":1,\"nombre\":\"Director\",\"esAdmin\":true,\"permisos\":{\"dashboard\":true,\"catalogo\":true,\"analisis\":false}}")))
    @APIResponse(responseCode = "401", description = "No autorizado")
    @APIResponse(responseCode = "404", description = "Rol no encontrado")
    @Transactional
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

    // DELETE /admin/roles/{idRol}
    @DELETE
    @Path("/{idRol}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Eliminar un rol",
            description = "Elimina un rol — falla si tiene usuarios asignados")
    @APIResponse(responseCode = "204", description = "Rol eliminado correctamente")
    @APIResponse(responseCode = "400", description = "El rol tiene usuarios asignados")
    @APIResponse(responseCode = "404", description = "Rol no encontrado")
    @APIResponse(responseCode = "401", description = "No autorizado")
    public Response eliminarRol(@PathParam("idRol") Integer idRol) {
        rolesRepository.findRoleById(idRol)
                .orElseThrow(() -> new NotFoundException(
                        "Rol con id " + idRol + " no encontrado"
                ));

        rolesRepository.deleteRole(idRol);
        // 204 No Content — eliminado exitosamente, sin body
        return Response.noContent().build();
    }
}