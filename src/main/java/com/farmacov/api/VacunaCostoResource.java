package com.farmacov.api;

import com.farmacov.application.dto.ActualizarVacunaCostoDto;
import com.farmacov.application.dto.CrearVacunaCostoDto;
import com.farmacov.application.usecase.VacunaCostoUseCase;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/admin/vacuna-costos")
@Tag(name = "Admin - Costos de Vacuna",
        description = "Gestión del historial de costos de vacunas")
public class VacunaCostoResource {

    @Inject
    VacunaCostoUseCase vacunaCostoUseCase;

    // GET /admin/vacuna-costos/vacuna/{idVacuna}
    @GET
    @Path("/vacuna/{idVacuna}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar costos por vacuna",
            description = "Obtiene el historial de costos de una vacuna específica")
    @APIResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @APIResponse(responseCode = "404", description = "Vacuna no encontrada")
    public Response getByVacuna(@PathParam("idVacuna") Integer idVacuna) {
        return Response.ok(
                vacunaCostoUseCase.obtenerPorVacuna(idVacuna)
        ).build();
    }

    // GET /admin/vacuna-costos/{id}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener costo por ID")
    @APIResponse(responseCode = "200", description = "Costo encontrado")
    @APIResponse(responseCode = "404", description = "No encontrado")
    public Response getById(@PathParam("id") Integer id) {
        return Response.ok(
                vacunaCostoUseCase.obtenerPorId(id)
        ).build();
    }

    // POST /admin/vacuna-costos
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Registrar costo de vacuna",
            description = "Agrega un nuevo registro de costo para una vacuna")
    @APIResponse(responseCode = "201", description = "Costo registrado correctamente")
    @APIResponse(responseCode = "400", description = "Datos inválidos")
    @APIResponse(responseCode = "404", description = "Vacuna no encontrada")
    public Response crear(@Valid CrearVacunaCostoDto dto) {
        return Response.status(201)
                .entity(vacunaCostoUseCase.crear(dto))
                .build();
    }

    // PUT /admin/vacuna-costos/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Actualizar costo de vacuna",
            description = "Corrige el costo unitario de un registro existente")
    @APIResponse(responseCode = "200", description = "Costo actualizado")
    @APIResponse(responseCode = "404", description = "No encontrado")
    public Response editar(
            @PathParam("id") Integer id,
            @Valid ActualizarVacunaCostoDto dto
    ) {
        return Response.ok(
                vacunaCostoUseCase.actualizar(id, dto)
        ).build();
    }

    // DELETE /admin/vacuna-costos/{id}
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar costo de vacuna",
            description = "Elimina un registro de costo por su ID")
    @APIResponse(responseCode = "204", description = "Eliminado correctamente")
    @APIResponse(responseCode = "404", description = "No encontrado")
    public Response eliminar(@PathParam("id") Integer id) {
        vacunaCostoUseCase.eliminar(id);
        return Response.noContent().build();
    }
}