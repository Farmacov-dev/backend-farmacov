package com.farmacov.api;

import com.farmacov.application.dto.KpisDashboardDto;
import com.farmacov.application.usecase.ObtenerKpisDashboardUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dashboard/kpis")
@ApplicationScoped
public class KpisDashboardResource {

    @Inject
    ObtenerKpisDashboardUseCase obtenerKpisDashboardUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKpis() {
        KpisDashboardDto kpis = obtenerKpisDashboardUseCase.execute();
        return Response.ok(kpis).build();
    }
}