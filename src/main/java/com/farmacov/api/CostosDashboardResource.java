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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/dashboard/costos")
@ApplicationScoped
@Tag(name = "Dashboard", description = "Endpoints del portal de análisis")

public class CostosDashboardResource {

    @Inject
    ObtenerDashboardCostos obtenerDashboardCostos;

    @Inject
    VacunaCostoUseCase vacunaCostoUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Obtener costos de todas las vacunas",
            description = "Regresa el costo unitario más reciente de cada vacuna disponible en el catálogo — usado para la gráfica de costos del dashboard"
    )

    @APIResponse(
            responseCode = "200",
            description = "Lista de costos obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "costos por vacuna",
                            value = "[{\"nombreVacuna\":\"Comirnaty\",\"costoUnitario\":299.99},{\"nombreVacuna\":\"Spikevax\",\"costoUnitario\":250.00},{\"nombreVacuna\":\"Vaxzevria\",\"costoUnitario\":45.00}]"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")


    public Response getCostos() {
        List<CostosPorVacunaDto> costos = obtenerDashboardCostos.execute();
        return Response.ok(costos).build();
    }

    @GET
    @Path("/{idVacuna}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Obtener costo por vacuna",
            description = "Regresa todos los registros de costo de una vacuna específica"
    )

    @Parameter(name = "idVacuna", description = "ID de la vacuna", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Historial de costos obtenido correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "historial costos",
                            value = "[{\"id\":1,\"idVacuna\":1,\"costoUnitario\":299.99,\"creadoEn\":\"2026-01-15T10:00:00\"},{\"id\":2,\"idVacuna\":1,\"costoUnitario\":275.50,\"creadoEn\":\"2026-03-01T10:00:00\"}]"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Vacuna no encontrada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrada",
                            value = "{\"error\": \"Vacuna con id 99 no encontrada\"}"
                    )
            )
    )

    public Response getCostosPorVacuna(@PathParam("idVacuna") Integer idVacuna) {
        List<VacunaCosto> costos = vacunaCostoUseCase.obtenerPorVacuna(idVacuna);
        return Response.ok(costos).build();
    }
}