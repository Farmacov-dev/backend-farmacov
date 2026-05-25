package com.farmacov.api;

import com.farmacov.application.dto.HistorialKpisDto;
import com.farmacov.application.usecase.ObtenerHistorialKpisUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/historial")
public class HistorialResource {

    @Inject
    ObtenerHistorialKpisUseCase obtenerHistorialKpisUseCase;

    @GET
    @Path("/kpis")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKpis() {
        HistorialKpisDto kpis = obtenerHistorialKpisUseCase.execute();
        return Response.ok(kpis).build();
    }
}
