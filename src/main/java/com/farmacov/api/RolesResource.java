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

import java.util.List;

@Path("/roles")
@ApplicationScoped
public class RolesResource {

    @Inject
    ObtenerTodosLosRoles obtenerTodosLosRoles; // inyecta el use case

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Roles> roles = obtenerTodosLosRoles.execute(); // llama al use case que llama al repo
        return Response.ok(roles).build(); // devuelve la lista en json
    }
}
