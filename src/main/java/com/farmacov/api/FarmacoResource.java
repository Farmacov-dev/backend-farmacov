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

@Path("/admin/farmacos")
@Tag(name = "Admin - Fármacos",
        description = "Gestión de fármacos — tabla padre de vacunas")
public class FarmacoResource {

    @Inject
    FarmacoUseCase farmacoUseCase;

    // GET /admin/farmacos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar todos los fármacos")
    @APIResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public Response getAll() {
        return Response.ok(farmacoUseCase.obtenerTodos()).build();
    }

    // GET /admin/farmacos/{id}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener fármaco por ID")
    @APIResponse(responseCode = "200", description = "Fármaco encontrado")
    @APIResponse(responseCode = "404", description = "No encontrado")
    public Response getById(@PathParam("id") Integer id) {
        return Response.ok(farmacoUseCase.obtenerPorId(id)).build();
    }

    // POST /admin/farmacos
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear fármaco",
            description = "Registra un nuevo fármaco en el sistema")
    @APIResponse(responseCode = "201", description = "Fármaco creado correctamente")
    @APIResponse(responseCode = "400", description = "Datos inválidos")
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
    @APIResponse(responseCode = "200", description = "Fármaco actualizado")
    @APIResponse(responseCode = "404", description = "No encontrado")
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
    @APIResponse(responseCode = "204", description = "Eliminado correctamente")
    @APIResponse(responseCode = "400", description = "Tiene vacunas asociadas")
    @APIResponse(responseCode = "404", description = "No encontrado")
    public Response eliminar(@PathParam("id") Integer id) {
        farmacoUseCase.eliminar(id);
        return Response.noContent().build();
    }
}