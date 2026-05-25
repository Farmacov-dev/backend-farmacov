package com.farmacov.interfaces.rest;

import com.farmacov.application.dto.BitacoraResponseDto;
import com.farmacov.application.usecase.ObtenerBitacoraUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/bitacora")
@Produces(MediaType.APPLICATION_JSON)
public class BitacoraResource {

    @Inject
    ObtenerBitacoraUseCase obtenerBitacoraUseCase;

    @GET
    public Response getAll() {
        List<BitacoraResponseDto> entradas = obtenerBitacoraUseCase.execute();
        return Response.ok(entradas).build();
    }
}
