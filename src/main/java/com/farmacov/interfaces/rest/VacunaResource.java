package com.farmacov.interfaces.rest;

import com.farmacov.application.dto.CrearVacunaDto;
import com.farmacov.application.dto.VacunaCatalogoResponseDto;
import com.farmacov.application.dto.VacunaDetalleResponseDto;
import com.farmacov.application.usecase.GetVacunaDetalleUseCase;
import com.farmacov.application.usecase.GetVacunasCatalogoUseCase;
import com.farmacov.application.usecase.VacunaUseCase;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;

@Path("/vacunas")
@Produces(MediaType.APPLICATION_JSON)
public class VacunaResource {

    @Inject
    GetVacunasCatalogoUseCase getVacunasCatalogoUseCase;

    @Inject
    GetVacunaDetalleUseCase getVacunaDetalleUseCase;

    @Inject
    VacunaUseCase vacunaUseCase;

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


    // POST /vacunas/{id}
    // El id es manual — se pasa en la URL igual que en los inserts de MySQL
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear vacuna",
            description = "El ID es manual — debe ser único y no existir previamente")
    @APIResponse(responseCode = "201", description = "Vacuna creada correctamente")
    @APIResponse(responseCode = "400", description = "Datos inválidos")
    @APIResponse(responseCode = "404", description = "Fármaco no encontrado")
    @APIResponse(responseCode = "409", description = "Ya existe una vacuna con ese ID")
    public Response crear(
            @PathParam("id") Integer id,
            @Valid CrearVacunaDto dto
    ) {
        return Response.status(201)
                .entity(vacunaUseCase.crear(id, dto))
                .build();
    }

    // PUT /vacunas/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Editar vacuna",
            description = "Actualiza nombre, farmacéutica, tipo y descripción")
    @APIResponse(responseCode = "200", description = "Vacuna actualizada")
    @APIResponse(responseCode = "404", description = "No encontrada")
    public Response editar(
            @PathParam("id") Integer id,
            @Valid CrearVacunaDto dto
    ) {
        return Response.ok(vacunaUseCase.actualizar(id, dto)).build();
    }

    // DELETE /vacunas/{id}
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar vacuna",
            description = "Elimina una vacuna solo si no tiene reportes adversos")
    @APIResponse(responseCode = "204", description = "Vacuna eliminada")
    @APIResponse(responseCode = "400", description = "Tiene reportes adversos")
    @APIResponse(responseCode = "404", description = "No encontrada")
    public Response eliminar(@PathParam("id") Integer id) {
        vacunaUseCase.eliminar(id);
        return Response.noContent().build();
    }



}
