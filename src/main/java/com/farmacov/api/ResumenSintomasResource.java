package com.farmacov.api;

import com.farmacov.application.dto.ResumenSintomasDto;
import com.farmacov.application.usecase.ObtenerResumenSintomasUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

//imports de documentacion

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

//

@Path("/dashboard/resumen-sintomas")
@Tag(name = "Dashboard", description = "endpoints del portal de analisis")
public class ResumenSintomasResource {

    @Inject
    ObtenerResumenSintomasUseCase obtenerResumenSintomasUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "resumen de sintomas por filtros",
            description = " regresa conteo de reportes agrupado por sintoma mas filtros opcionables y combinables"
    )

    @Parameter(name = "IdVacuna", description = "filtrar por id de vacuna", example = "1")
    @Parameter(name = "sexo", description = "filtra por sexo: masculino, femenino o desconocido", example = "F")
    @Parameter(name = "grupoEdad", description = "filtrar por grupo de edad: 0-17, 18-29, 30-49, 50-64, 65+, DESCONOCIDO", example = "18-29")
    @Parameter(name = "esGrave",   description = "filtrar solo reportes graves (true) o leves (false)", example = "true")
    @Parameter(name = "limit",     description = "limite de resultados a retornar", example = "10")

    @APIResponse(
            responseCode = "200",
            description = "resumen de sintomas obtenido correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "resumen sintomas",
                            value = "[{\"idVacuna\":1,\"nombreVacuna\":\"Pfizer\",\"idSintoma\":3,\"nombreSintoma\":\"Anafilaxia\",\"sexo\":\"F\",\"grupoEdad\":\"18-29\",\"esGrave\":true,\"total\":42}]"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "no autorizado")




    public Response get(
            @QueryParam("idVacuna")  Integer idVacuna,
            @QueryParam("sexo")      String  sexo,
            @QueryParam("grupoEdad") String  grupoEdad,
            @QueryParam("esGrave")   Boolean esGrave,
            @QueryParam("limit")     Integer limit
    ) {
        List<ResumenSintomasDto> resultado =
                obtenerResumenSintomasUseCase.execute(idVacuna, sexo, grupoEdad, esGrave, limit);
        return Response.ok(resultado).build();
    }
}