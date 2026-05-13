package com.farmacov.api;

import com.farmacov.application.dto.UltimaActualizacionDto;
import com.farmacov.application.usecase.ObtenerUltimaActualizacionUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dashboard/ultima-actualizacion")
@ApplicationScoped
public class UltimaActualizacionResource {

    @Inject
    ObtenerUltimaActualizacionUseCase obtenerUltimaActualizacionUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUltimaActualizacion() {
        UltimaActualizacionDto dto = obtenerUltimaActualizacionUseCase.execute();
        return Response.ok(dto).build();
    }
}