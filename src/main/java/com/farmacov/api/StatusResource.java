package com.farmacov.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
//imports de documentacion
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;

@Path("/status")
@Tag(name = "Sistema", description = "Endpoint de salud del servidor ")

public class StatusResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "verifica estado de servidor",
            description = "regresa estado actual de aplicacion, sin autenticacion"
    )

    @APIResponse(
            responseCode = "200",
            description = "servidor funciona correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "respuesta exitosa",
                            value = "{\"status\":\"ok\",\"app\":\"farmacov-backend\"}"
                    )
            )
    )

    public Response status() {
        return Response.ok("{\"status\":\"ok\",\"app\":\"farmacov-backend\"}").build();
    }
}