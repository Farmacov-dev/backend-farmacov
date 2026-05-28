package com.farmacov.api;

import com.farmacov.application.dto.ActualizarSintomaGraveDto;
import com.farmacov.application.dto.CrearSintomaGraveDto;
import com.farmacov.application.usecase.SintomaGraveUseCase;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/admin/sintomas-graves")
@Tag(name = "Admin - Síntomas Graves", description = "Gestión de síntomas graves de vacunas")
// Resource administrativo para consultar y administrar sintomas graves asociados a vacunas.
public class SintomasGravesResource {

    @Inject
    SintomaGraveUseCase sintomaGraveUseCase;

    // GET /admin/sintomas-graves
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar todos los síntomas graves")
    @APIResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public Response getAll() {
        return Response.ok(sintomaGraveUseCase.obtenerTodos()).build();
    }

    // GET /admin/sintomas-graves/vacuna/{idVacuna}
    @GET
    @Path("/vacuna/{idVacuna}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar síntomas graves por vacuna")
    @APIResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @APIResponse(responseCode = "404", description = "Vacuna no encontrada")
    public Response getByVacuna(@PathParam("idVacuna") Integer idVacuna) {
        return Response.ok(sintomaGraveUseCase.obtenerPorVacuna(idVacuna)).build();
    }

    // GET /admin/sintomas-graves/{id}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener síntoma grave por ID")
    @APIResponse(responseCode = "200", description = "Síntoma encontrado")
    @APIResponse(responseCode = "404", description = "No encontrado")
    public Response getById(@PathParam("id") Integer id) {
        return Response.ok(sintomaGraveUseCase.obtenerPorId(id)).build();
    }

    // POST /admin/sintomas-graves
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear síntoma grave",
            description = "Registra un nuevo síntoma grave asociado a una vacuna")
    @APIResponse(responseCode = "201", description = "Síntoma creado correctamente")
    @APIResponse(responseCode = "400", description = "Datos inválidos")
    @APIResponse(responseCode = "404", description = "Vacuna no encontrada")
    public Response crear(@Valid CrearSintomaGraveDto dto) {
        return Response.status(201)
                .entity(sintomaGraveUseCase.crear(dto))
                .build();
    }

    // PUT /admin/sintomas-graves/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Editar síntoma grave",
            description = "Actualiza el nombre de un síntoma existente")
    @APIResponse(responseCode = "200", description = "Síntoma actualizado")
    @APIResponse(responseCode = "404", description = "No encontrado")
    public Response editar(
            @PathParam("id") Integer id,
            @Valid ActualizarSintomaGraveDto dto
    ) {
        return Response.ok(sintomaGraveUseCase.actualizar(id, dto)).build();
    }

    // DELETE /admin/sintomas-graves/{id}
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar síntoma grave",
            description = "Elimina un síntoma solo si no tiene reportes adversos asociados")
    @APIResponse(responseCode = "204", description = "Eliminado correctamente")
    @APIResponse(responseCode = "400", description = "Tiene reportes adversos — no se puede eliminar")
    @APIResponse(responseCode = "404", description = "No encontrado")
    public Response eliminar(@PathParam("id") Integer id) {
        sintomaGraveUseCase.eliminar(id);
        return Response.noContent().build();
    }
}
