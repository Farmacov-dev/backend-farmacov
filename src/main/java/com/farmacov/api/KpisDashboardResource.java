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

// imports de documentacion

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

//

@Path("/dashboard/kpis")
@Tag(name = "Dashboard", description = "endpoints del portal de analisis")
public class KpisDashboardResource {

    @Inject
    ObtenerKpisDashboardUseCase obtenerKpisDashboardUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)

    @Operation (
            summary = "KPIs del dashboard",
            description = "regresan indicadores claves del sistema para el componente de cards"
    )

    @APIResponse(
            responseCode = "200",
            description = "KPIs obtenidos correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "kpis",
                            value = "{\"totalVacunas\":12,\"totalReportes\":4823,\"reportesEsteMes\":318," +
                                    "\"totalReportesGraves\":241,\"porcentajeReportesGraves\":4.99}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado")


    public Response getKpis() {
        KpisDashboardDto kpis = obtenerKpisDashboardUseCase.execute();
        return Response.ok(kpis).build();
    }
}