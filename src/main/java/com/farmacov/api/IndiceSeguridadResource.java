package com.farmacov.api;

import com.farmacov.application.dto.IndiceSeguridadDto;
import com.farmacov.application.usecase.ObtenerIndiceVacunaUseCase;
import com.farmacov.application.usecase.ObtenerTodosLosIndicesUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

// imports de documentacion

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

//

@Path("/dashboard/indice-seguridad")
@Tag(name = "Dashboard", description = "endpoints del portal de analisis")
public class IndiceSeguridadResource {

    @Inject ObtenerIndiceVacunaUseCase    obtenerIndiceVacunaUseCase;
    @Inject ObtenerTodosLosIndicesUseCase obtenerTodosLosIndicesUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)


    @Operation(
            summary = "Índice de seguridad de todas las vacunas",
            description = "Devuelve el índice de seguridad calculado para cada vacuna. " +
                    "Se obtiene desde la VIEW vista_indice_seguridad — " +
                    "fórmula: 100 * (1 - (reportesGraves / totalReportes)). " +
                    "Vacunas sin reportes devuelven indiceSeguridad null"
    )

    @APIResponse(
            responseCode = "200",
            description =  "indices obtenidos corectamnete",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "todos los indices",
                            value = "[{\"idVacuna\":1,\"nombreVacuna\":\"Pfizer\",\"totalReportes\":4823," +
                                    "\"reportesGraves\":241,\"indiceSeguridad\":0.05}," +
                                    "{\"idVacuna\":2,\"nombreVacuna\":\"AstraZeneca\",\"totalReportes\":3100," +
                                    "\"reportesGraves\":189,\"indiceSeguridad\":0.06}]"
                    )
            )
    )

    @APIResponse( responseCode = "401", description = "no autorizado")


    public Response getTodos() {
        List<IndiceSeguridadDto> indices = obtenerTodosLosIndicesUseCase.execute();
        return Response.ok(indices).build();
    }

    @GET
    @Path("/{idVacuna}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Índice de seguridad por vacuna",
            description = "Devuelve el índice de seguridad de una vacuna específica. " +
                    "Se calcula con el stored procedure sp_indice_seguridad — " +
                    "fórmula: 100 * (1 - (reportesGraves / totalReportes)). " +
                    "Si la vacuna no tiene reportes, indiceSeguridad es null"
    )

    @Parameter(
            name = "idVacuna",
            description = "id de la vacuna a consultar",
            required = true,
            example = "1"
    )

    @APIResponse(
            responseCode = "200",
            description = "indice de seguridad obtenido correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "indice por vacuna",
                            value = "{\"idVacuna\":1,\"nombreVacuna\":\"Pfizer\",\"totalReportes\":4823," +
                                    "\"reportesGraves\":241,\"indiceSeguridad\":0.05}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado")

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

    public Response getPorVacuna(@PathParam("idVacuna") Integer idVacuna) {
        IndiceSeguridadDto indice = obtenerIndiceVacunaUseCase.execute(idVacuna);
        return Response.ok(indice).build();
    }
}