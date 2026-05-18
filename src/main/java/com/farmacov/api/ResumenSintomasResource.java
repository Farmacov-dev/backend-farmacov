package com.farmacov.api;

import com.farmacov.application.dto.ResumenSintomasDto;
import com.farmacov.application.usecase.ObtenerResumenSintomasUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/dashboard/resumen-sintomas")
@ApplicationScoped
public class ResumenSintomasResource {

    @Inject
    ObtenerResumenSintomasUseCase obtenerResumenSintomasUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
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