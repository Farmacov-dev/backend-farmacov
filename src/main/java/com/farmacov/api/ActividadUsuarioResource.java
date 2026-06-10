package com.farmacov.api;

import com.farmacov.application.dto.UltimaActividadUsuarioResponseDto;
import com.farmacov.application.usecase.ObtenerUltimaActividadUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/actividad")
@ApplicationScoped
@Tag(name = "Actividad", description = "Endpoints de actividad y auditoría de usuarios")

public class ActividadUsuarioResource {

    @Inject
    ObtenerUltimaActividadUseCase obtenerUltimaActividadUseCase;

    @GET
    @Path("/me/ultima")
    @Produces(MediaType.APPLICATION_JSON)


    @Operation(
            summary = "Obtener última actividad del usuario autenticado",
            description = "Regresa el último registro de actividad del usuario identificado por su token Firebase — endpoint, método HTTP, status code, IP y user agent"
    )

    @APIResponse(
            responseCode = "200",
            description = "Última actividad obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "ultima actividad",
                            value = "{\"idUsuario\":\"550e8400-e29b-41d4-a716-446655440000\",\"endpoint\":\"/dashboard/kpis\",\"metodoHttp\":\"GET\",\"statusCode\":200,\"queryString\":null,\"userAgent\":\"Mozilla/5.0\",\"ipCliente\":\"192.168.1.1\",\"creadoEn\":\"2026-05-27T10:30:00\",\"actualizadoEn\":\"2026-05-27T10:30:00\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "No hay actividad registrada para este usuario",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "sin actividad",
                            value = "{\"error\": \"No hay actividad registrada\"}"
                    )
            )
    )

    public Response getUltimaActividad(@Context ContainerRequestContext ctx) {
        String firebaseUid = (String) ctx.getProperty("firebase_uid");
        UltimaActividadUsuarioResponseDto actividad = obtenerUltimaActividadUseCase.execute(firebaseUid)
                .orElse(null);

        if (actividad == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"No hay actividad registrada\"}")
                    .build();
        }

        return Response.ok(actividad).build();
    }
}
