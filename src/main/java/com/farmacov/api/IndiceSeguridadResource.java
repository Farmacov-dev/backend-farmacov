package com.farmacov.api;

import com.farmacov.application.dto.IndiceSeguridadDto;
import com.farmacov.application.usecase.ObtenerIndiceVacunaUseCase;
import com.farmacov.application.usecase.ObtenerTodosLosIndicesUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/dashboard/indice-seguridad")
@ApplicationScoped
public class IndiceSeguridadResource {

    @Inject ObtenerIndiceVacunaUseCase    obtenerIndiceVacunaUseCase;
    @Inject ObtenerTodosLosIndicesUseCase obtenerTodosLosIndicesUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodos() {
        List<IndiceSeguridadDto> indices = obtenerTodosLosIndicesUseCase.execute();
        return Response.ok(indices).build();
    }

    @GET
    @Path("/{idVacuna}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPorVacuna(@PathParam("idVacuna") Integer idVacuna) {
        IndiceSeguridadDto indice = obtenerIndiceVacunaUseCase.execute(idVacuna);
        return Response.ok(indice).build();
    }
}