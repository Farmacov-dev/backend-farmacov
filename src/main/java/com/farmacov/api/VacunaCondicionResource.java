package com.farmacov.api;

import com.farmacov.application.dto.ActualizarVacunaCondicionDto;
import com.farmacov.application.dto.CrearVacunaCondicionDto;
import com.farmacov.application.usecase.VacunaCondicionUseCase;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/admin/vacuna-condiciones")
@Tag(name = "Admin - Condiciones de Vacuna",
        description = "Gestión de condiciones de almacenamiento de vacunas")
public class VacunaCondicionResource {

    @Inject
    VacunaCondicionUseCase vacunaCondicionUseCase;

    // GET /admin/vacuna-condiciones/vacuna/{idVacuna}
    @GET
    @Path("/vacuna/{idVacuna}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar condiciones por vacuna",
            description = "Obtiene todas las condiciones de almacenamiento de una vacuna")
    @APIResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @APIResponse(responseCode = "404", description = "Vacuna no encontrada")
    public Response getByVacuna(@PathParam("idVacuna") Integer idVacuna) {
        return Response.ok(
                vacunaCondicionUseCase.obtenerPorVacuna(idVacuna)
        ).build();
    }

    // GET /admin/vacuna-condiciones/{id}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener condición por ID")
    @APIResponse(responseCode = "200", description = "Condición encontrada")
    @APIResponse(responseCode = "404", description = "No encontrada")
    public Response getById(@PathParam("id") Integer id) {
        return Response.ok(
                vacunaCondicionUseCase.obtenerPorId(id)
        ).build();
    }

    // POST /admin/vacuna-condiciones
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear condición de almacenamiento",
            description = "Registra una nueva condición de almacenamiento para una vacuna")
    @APIResponse(responseCode = "201", description = "Condición creada correctamente")
    @APIResponse(responseCode = "400", description = "Datos inválidos")
    @APIResponse(responseCode = "404", description = "Vacuna no encontrada")
    public Response crear(@Valid CrearVacunaCondicionDto dto) {
        return Response.status(201)
                .entity(vacunaCondicionUseCase.crear(dto))
                .build();
    }

    // PUT /admin/vacuna-condiciones/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Editar condición de almacenamiento",
            description = "Actualiza temperatura y/o tiempo ambiente. " +
                    "tiempoAmbiente puede ser null para borrar el valor existente")
    @APIResponse(responseCode = "200", description = "Condición actualizada")
    @APIResponse(responseCode = "404", description = "No encontrada")
    public Response editar(
            @PathParam("id") Integer id,
            @Valid ActualizarVacunaCondicionDto dto
    ) {
        return Response.ok(
                vacunaCondicionUseCase.actualizar(id, dto)
        ).build();
    }

    // DELETE /admin/vacuna-condiciones/{id}
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar condición de almacenamiento",
            description = "Elimina una condición de almacenamiento por su ID")
    @APIResponse(responseCode = "204", description = "Eliminada correctamente")
    @APIResponse(responseCode = "404", description = "No encontrada")
    public Response eliminar(@PathParam("id") Integer id) {
        vacunaCondicionUseCase.eliminar(id);
        return Response.noContent().build();
    }
}