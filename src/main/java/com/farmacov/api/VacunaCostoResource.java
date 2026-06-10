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
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
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

    @Parameter(name = "idVacuna", description = "ID de la vacuna", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Lista obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "historial costos",
                            value = "[{\"id\":1,\"idVacuna\":1,\"costoUnitario\":299.99,\"creadoEn\":\"2026-01-15T10:00:00\"},{\"id\":2,\"idVacuna\":1,\"costoUnitario\":275.50,\"creadoEn\":\"2026-03-01T10:00:00\"}]"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")


    @APIResponse(
            responseCode = "404",
            description = "Vacuna no encontrada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrada",
                            value = "{\"error\": \"Vacuna con id 99 no encontrada\"}"
                    )
            )
    )


    public Response getByVacuna(@PathParam("idVacuna") Integer idVacuna) {
        return Response.ok(
                vacunaCostoUseCase.obtenerPorVacuna(idVacuna)
        ).build();
    }

    // GET /admin/vacuna-costos/{id}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Obtener costo por ID",
            description = "Busca y devuelve un registro de costo por su ID único"
    )

    @Parameter(name = "id", description = "ID del registro de costo", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Costo encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "costo",
                            value = "{\"id\":1,\"idVacuna\":1,\"costoUnitario\":299.99,\"creadoEn\":\"2026-01-15T10:00:00\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Costo no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"VacunaCosto con id 99 no encontrado\"}"
                    )
            )
    )

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

    @APIResponse(
            responseCode = "201",
            description = "Costo registrado correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "costo creado",
                            value = "{\"id\":3,\"idVacuna\":1,\"costoUnitario\":250.00,\"creadoEn\":\"2026-05-27T10:00:00\"}"
                    )
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "Datos inválidos — costo menor o igual a 0 o formato incorrecto",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "error validacion",
                            value = "{\"error\": \"costo debe ser mayor a 0\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Vacuna no encontrada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "vacuna no encontrada",
                            value = "{\"error\": \"Vacuna con id 99 no encontrada\"}"
                    )
            )
    )

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

    @Parameter(name = "id", description = "ID del registro de costo a editar", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Costo actualizado correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "costo actualizado",
                            value = "{\"id\":1,\"idVacuna\":1,\"costoUnitario\":225.00,\"creadoEn\":\"2026-01-15T10:00:00\"}"
                    )
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "Datos inválidos — costo menor o igual a 0",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "error validacion",
                            value = "{\"error\": \"costo debe ser mayor a 0\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Costo no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"VacunaCosto con id 99 no encontrado\"}"
                    )
            )
    )

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

    @Parameter(name = "id", description = "ID del registro de costo a eliminar", example = "1")

    @APIResponse(responseCode = "204", description = "Eliminado correctamente")

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Costo no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"VacunaCosto con id 99 no encontrado\"}"
                    )
            )
    )

    public Response eliminar(@PathParam("id") Integer id) {
        vacunaCostoUseCase.eliminar(id);
        return Response.noContent().build();
    }
}