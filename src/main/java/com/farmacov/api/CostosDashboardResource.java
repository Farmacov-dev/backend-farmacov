package com.farmacov.api;

import com.farmacov.application.dto.CostosPorVacunaDto;
import com.farmacov.application.usecase.ObtenerDashboardCostos;
import com.farmacov.application.usecase.VacunaCostoUseCase;
import com.farmacov.domain.models.VacunaCosto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/dashboard/costos")
@ApplicationScoped
public class CostosDashboardResource {

    @Inject
    ObtenerDashboardCostos obtenerDashboardCostos;

    @Inject
    VacunaCostoUseCase vacunaCostoUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCostos() {
        List<CostosPorVacunaDto> costos = obtenerDashboardCostos.execute();
        return Response.ok(costos).build();
    }

    @GET
    @Path("/{idVacuna}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCostosPorVacuna(@PathParam("idVacuna") Integer idVacuna) {
        List<VacunaCosto> costos = vacunaCostoUseCase.obtenerPorVacuna(idVacuna);
        return Response.ok(costos).build();
    }
}