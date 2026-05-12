package com.farmacov.api;
import com.farmacov.application.dto.CostosPorVacunaDto;
import com.farmacov.application.usecase.ObtenerDashboardCostos;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/dashboard/costos")
@ApplicationScoped
public class CostosDashboardResource {
    @Inject
    ObtenerDashboardCostos obtenerDashboardCostos; // iuse case

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCostos() {
        List<CostosPorVacunaDto> costos = obtenerDashboardCostos.execute(); // llama al use case
        return Response.ok(costos).build(); // devuelve 200 + lista en JSON
    }




}
