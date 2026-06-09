package com.farmacov.interfaces.rest;

import com.farmacov.application.dto.BitacoraResponseDto;
import com.farmacov.application.dto.PaginatedResponseDto;
import com.farmacov.application.usecase.ObtenerBitacoraUseCase;
import com.farmacov.application.usecase.ObtenerBitacoraPaginadaUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/bitacora")
@Produces(MediaType.APPLICATION_JSON)
public class BitacoraResource {
    @Inject
    ObtenerBitacoraUseCase obtenerBitacoraUseCase;

    @Inject
    ObtenerBitacoraPaginadaUseCase obtenerBitacoraPaginadaUseCase;

    @GET
    public Response getAll() {
        List<BitacoraResponseDto> entradas = obtenerBitacoraUseCase.execute();
        return Response.ok(entradas).build();
    }

    @GET
    @Path("/paginated")
    public Response getPaginated(@QueryParam("page") @DefaultValue("0") int page) {
        if (page < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El número de página no puede ser negativo")
                    .build();
        }
        PaginatedResponseDto<BitacoraResponseDto> result = obtenerBitacoraPaginadaUseCase.execute(page);
        return Response.ok(result).build();
    }
}
