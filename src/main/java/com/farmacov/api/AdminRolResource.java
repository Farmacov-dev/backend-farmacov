package com.farmacov.api;

import com.farmacov.domain.models.Roles;
import com.farmacov.domain.repository.RolesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/admin/roles")
@ApplicationScoped
public class AdminRolResource {

    @Inject
    RolesRepository rolesRepository;

    // GET /admin/roles — lista todos los roles con sus permisos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
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