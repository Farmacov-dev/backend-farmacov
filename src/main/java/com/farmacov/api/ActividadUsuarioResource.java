package com.farmacov.api;

import com.farmacov.application.dto.UltimaActividadUsuarioResponseDto;
import com.farmacov.application.usecase.ObtenerUltimaActividadUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.container.ContainerRequestContext;

@Path("/actividad")
@ApplicationScoped
public class ActividadUsuarioResource {

    @Inject
    ObtenerUltimaActividadUseCase obtenerUltimaActividadUseCase;

    @GET
    @Path("/me/ultima")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUltimaActividad(@Context ContainerRequestContext ctx) {
        String firebaseUid = (String) ctx.getProperty("firebase_uid");
        UltimaActividadUsuarioResponseDto actividad = obtenerUltimaActividadUseCase.execute(firebaseUid)
                .orElse(null);

        if (actividad == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"No hay actividad registrada\"}")
                    .build();
        }

        return Response.ok(actividad).build();
    }
}
