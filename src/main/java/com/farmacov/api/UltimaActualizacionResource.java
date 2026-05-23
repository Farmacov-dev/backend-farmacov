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

// imports de documentacion

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

//

@Path("/dashboard/ultima-actualizacion")
@Tag(name = "Dashboard", description = "endpoints del portal de analisis")
public class UltimaActualizacionResource {

    @Inject
    ObtenerUltimaActualizacionUseCase obtenerUltimaActualizacionUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "ultima actualizacion de la base de datos",
            description =  "regresa la fecha y hora de la ultima insercion a reportes adversos"
    )
    @APIResponse(
            responseCode = "200",
            description = "fecha de ultima actualizacion obtenida correctamnete",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "ultima actualizacion",
                            value = "{\"fecha\":\"2026-05-07T21:00:00\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "no autorizado")


    public Response getUltimaActualizacion() {
        UltimaActualizacionDto dto = obtenerUltimaActualizacionUseCase.execute();
        return Response.ok(dto).build();
    }
}