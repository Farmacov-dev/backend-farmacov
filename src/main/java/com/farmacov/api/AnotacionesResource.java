package com.farmacov.api;

import com.farmacov.application.dto.ActualizarAnotacionDto;
import com.farmacov.application.dto.CrearAnotacionDto;
import com.farmacov.application.usecase.AnotacionUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;

@Path("/anotaciones")
@Tag(name = "Anotaciones", description = "Gestion de anotaciones del sistema")
@ApplicationScoped
public class AnotacionesResource {

    @Inject
    AnotacionUseCase anotacionUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar anotaciones")
    @APIResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public Response listar() {
        return Response.ok(anotacionUseCase.listarTodas()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener anotacion por ID")
    @APIResponse(responseCode = "200", description = "Anotacion encontrada")
    @APIResponse(responseCode = "404", description = "No encontrada")
    public Response obtenerPorId(@PathParam("id") Integer id) {
        return Response.ok(anotacionUseCase.obtenerPorId(id)).build();
    }

    @GET
    @Path("/usuario/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar anotaciones por usuario")
    @APIResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public Response listarPorUsuario(@PathParam("idUsuario") UUID idUsuario) {
        return Response.ok(anotacionUseCase.listarPorUsuario(idUsuario)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear anotacion")
    @APIResponse(responseCode = "201", description = "Anotacion creada correctamente")
    @APIResponse(responseCode = "400", description = "Datos invalidos")
    public Response crear(@Valid CrearAnotacionDto dto) {
        return Response.status(201)
                .entity(anotacionUseCase.crear(dto))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Actualizar anotacion")
    @APIResponse(responseCode = "200", description = "Anotacion actualizada correctamente")
    @APIResponse(responseCode = "404", description = "No encontrada")
    public Response actualizar(@PathParam("id") Integer id, @Valid ActualizarAnotacionDto dto) {
        return Response.ok(anotacionUseCase.actualizar(id, dto)).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar anotacion")
    @APIResponse(responseCode = "204", description = "Anotacion eliminada correctamente")
    public Response eliminar(@PathParam("id") Integer id) {
        anotacionUseCase.eliminar(id);
        return Response.noContent().build();
    }
}
