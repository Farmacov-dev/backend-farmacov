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
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
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

    @Parameter(name = "idVacuna", description = "ID de la vacuna", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Lista obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "condiciones por vacuna",
                            value = "[{\"id\":1,\"idVacuna\":1,\"temperatura\":-70.0,\"tiempoAmbiente\":2.0}]"
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
                vacunaCondicionUseCase.obtenerPorVacuna(idVacuna)
        ).build();
    }


    // GET /admin/vacuna-condiciones/{id}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Obtener condición por ID",
            description = "Busca y devuelve una condición de almacenamiento por su ID único"
    )

    @Parameter(name = "id", description = "ID de la condición de almacenamiento", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Condición encontrada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "condicion",
                            value = "{\"id\":1,\"idVacuna\":1,\"temperatura\":-70.0,\"tiempoAmbiente\":2.0}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Condición no encontrada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrada",
                            value = "{\"error\": \"VacunaCondicion con id 99 no encontrada\"}"
                    )
            )
    )

    public Response getById(@PathParam("id") Integer id) {
        return Response.ok(
                vacunaCondicionUseCase.obtenerPorId(id)
        ).build();
    }

    // POST /admin/vacuna-condiciones
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Crear condición de almacenamiento",
            description = "Registra una nueva condición de almacenamiento para una vacuna. " +
                    "temperatura es obligatoria — rango: -999.9 a 9999.9 con 1 decimal. " +
                    "tiempoAmbiente es opcional — debe ser mayor a 0 si se envía"
    )

    @APIResponse(
            responseCode = "201",
            description = "Condición creada correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "condicion creada",
                            value = "{\"id\":2,\"idVacuna\":1,\"temperatura\":2.0,\"tiempoAmbiente\":null}"
                    )
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "Datos inválidos — temperatura fuera de rango o formato incorrecto",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "error validacion",
                            value = "{\"error\": \"La temperatura es obligatoria\"}"
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

    @Operation(
            summary = "Editar condición de almacenamiento",
            description = "Actualiza temperatura y/o tiempo ambiente de una condición existente. " +
                    "La vacuna asociada no cambia. " +
                    "Mandar tiempoAmbiente como null borrará el valor existente en la BD"
    )

    @Parameter(name = "id", description = "ID de la condición a editar", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Condición actualizada correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "condicion actualizada",
                            value = "{\"id\":1,\"idVacuna\":1,\"temperatura\":-80.0,\"tiempoAmbiente\":4.0}"
                    )
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "Datos inválidos — temperatura fuera de rango o formato incorrecto",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "error validacion",
                            value = "{\"error\": \"La temperatura mínima permitida es -999.9\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Condición no encontrada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrada",
                            value = "{\"error\": \"VacunaCondicion con id 99 no encontrada\"}"
                    )
            )
    )
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

    @Parameter(name = "id", description = "ID de la condición a eliminar", example = "1")

    @APIResponse(responseCode = "204", description = "Eliminada correctamente")

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Condición no encontrada",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrada",
                            value = "{\"error\": \"VacunaCondicion con id 99 no encontrada\"}"
                    )
            )
    )

    public Response eliminar(@PathParam("id") Integer id) {
        vacunaCondicionUseCase.eliminar(id);
        return Response.noContent().build();
    }
}