package com.farmacov.api;

import com.farmacov.application.dto.HistorialKpisDto;
import com.farmacov.application.usecase.ObtenerHistorialKpisUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/historial")
@Tag(name = "Historial", description = "Endpoints de historial y métricas de usuarios del sistema")
public class HistorialResource {

    @Inject
    ObtenerHistorialKpisUseCase obtenerHistorialKpisUseCase;

    @GET
    @Path("/kpis")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Obtener KPIs de usuarios",
            description = "Devuelve el conteo de usuarios activos y suspendidos en el sistema — usado para métricas de administración"
    )

    @APIResponse(
            responseCode = "200",
            description = "KPIs de usuarios obtenidos correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "kpis usuarios",
                            value = "{\"usuariosActivos\":8,\"usuariosSuspendidos\":2}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")


    public Response getKpis() {
        HistorialKpisDto kpis = obtenerHistorialKpisUseCase.execute();
        return Response.ok(kpis).build();
    }
}
