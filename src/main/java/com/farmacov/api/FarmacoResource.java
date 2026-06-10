package com.farmacov.api;

import com.farmacov.application.dto.CrearFarmacoDto;
import com.farmacov.application.usecase.FarmacoUseCase;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;


@Path("/admin/farmacos")
@Tag(name = "Admin - Fármacos",
        description = "Gestión de fármacos — tabla padre de vacunas")
public class FarmacoResource {

    @Inject
    FarmacoUseCase farmacoUseCase;

    // GET /admin/farmacos
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Listar todos los fármacos",
            description = "Devuelve todos los fármacos registrados en el sistema, tabla padre de las vacunas"
    )

    @APIResponse(
            responseCode = "200",
            description = "Lista obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "lista farmacos",
                            value = "[{\"id\":1,\"nombre\":\"Pfizer Inc\",\"tipo\":\"Biológico\",\"descripcion\":\"Laboratorio farmacéutico multinacional estadounidense\"},{\"id\":2,\"nombre\":\"Moderna\",\"tipo\":\"Biológico\",\"descripcion\":\"Empresa biotecnológica especializada en ARNm\"}]"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    public Response getAll() {
        return Response.ok(farmacoUseCase.obtenerTodos()).build();
    }

    // GET /admin/farmacos/{id}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Obtener fármaco por ID",
            description = "Busca y devuelve un fármaco por su ID único"
    )

    @Parameter(name = "id", description = "ID del fármaco", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Fármaco encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "farmaco",
                            value = "{\"id\":1,\"nombre\":\"Pfizer Inc\",\"tipo\":\"Biológico\",\"descripcion\":\"Laboratorio farmacéutico multinacional estadounidense\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Fármaco no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"Farmaco con id 99 no encontrado\"}"
                    )
            )
    )

    public Response getById(@PathParam("id") Integer id) {
        return Response.ok(farmacoUseCase.obtenerPorId(id)).build();
    }



    // POST /admin/farmacos
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(summary = "Crear fármaco",
            description = "Registra un nuevo fármaco en el sistema")

    @APIResponse(
            responseCode = "201",
            description = "Fármaco creado correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "farmaco creado",
                            value = "{\"id\":3,\"nombre\":\"AstraZeneca\",\"tipo\":\"Biológico\",\"descripcion\":\"Laboratorio farmacéutico británico-sueco\"}"
                    )
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "Datos inválidos — nombre o tipo faltantes",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "error validacion",
                            value = "{\"error\": \"El nombre del fármaco es obligatorio\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    public Response crear(@Valid CrearFarmacoDto dto) {
        return Response.status(201)
                .entity(farmacoUseCase.crear(dto))
                .build();
    }

    // PUT /admin/farmacos/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(summary = "Editar fármaco",
            description = "Actualiza nombre, tipo y descripción de un fármaco")

    @Parameter(name = "id", description = "ID del fármaco a editar", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Fármaco actualizado correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "farmaco actualizado",
                            value = "{\"id\":1,\"nombre\":\"Pfizer Inc\",\"tipo\":\"Biológico\",\"descripcion\":\"Descripción actualizada\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Fármaco no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"Farmaco con id 99 no encontrado\"}"
                    )
            )
    )

    public Response editar(
            @PathParam("id") Integer id,
            @Valid CrearFarmacoDto dto
    ) {
        return Response.ok(farmacoUseCase.actualizar(id, dto)).build();
    }

    // DELETE /admin/farmacos/{id}
    @DELETE
    @Path("/{id}")

    @Operation(summary = "Eliminar fármaco",
            description = "Elimina un fármaco — falla si tiene vacunas asociadas")

    @Parameter(name = "id", description = "ID del fármaco a eliminar", example = "1")

    @APIResponse(responseCode = "204", description = "Eliminado correctamente")

    @APIResponse(
            responseCode = "400",
            description = "No se puede eliminar — tiene vacunas asociadas",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "error fk",
                            value = "{\"error\": \"No se puede eliminar — tiene 3 vacuna(s) asociada(s)\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")


    @APIResponse(
            responseCode = "404",
            description = "Fármaco no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"Farmaco con id 99 no encontrado\"}"
                    )
            )
    )

    public Response eliminar(@PathParam("id") Integer id) {
        farmacoUseCase.eliminar(id);
        return Response.noContent().build();
    }
}