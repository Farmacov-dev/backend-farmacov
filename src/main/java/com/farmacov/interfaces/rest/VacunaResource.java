package com.farmacov.interfaces.rest;

import com.farmacov.application.dto.VacunaCatalogoResponseDto;
import com.farmacov.application.dto.VacunaDetalleResponseDto;
import com.farmacov.application.usecase.GetVacunaDetalleUseCase;
import com.farmacov.application.usecase.GetVacunasCatalogoUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/vacunas")
@Produces(MediaType.APPLICATION_JSON)
public class VacunaResource {

    @Inject
    GetVacunasCatalogoUseCase getVacunasCatalogoUseCase;

    @Inject
    GetVacunaDetalleUseCase getVacunaDetalleUseCase;

    @GET
    public Response getCatalogo() {
        List<VacunaCatalogoResponseDto> catalogo = getVacunasCatalogoUseCase.execute();
        return Response.ok(catalogo).build();
    }

    @GET
    @Path("/{id}")
    public Response getDetalle(@PathParam("id") Integer id) {
        VacunaDetalleResponseDto detalle = getVacunaDetalleUseCase.execute(id);
        return Response.ok(detalle).build();
    }
}
