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
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
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

    @Operation(
            summary = "Listar todos los síntomas graves",
            description = "Devuelve todos los síntomas graves registrados en el sistema sin importar la vacuna"
    )

    @APIResponse(
            responseCode = "200",
            description = "Lista obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "todos los sintomas",
                            value = "[{\"id\":1,\"idVacuna\":1,\"nombre\":\"Miocarditis\"},{\"id\":2,\"idVacuna\":1,\"nombre\":\"Anafilaxia\"},{\"id\":3,\"idVacuna\":2,\"nombre\":\"Trombosis\"}]"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    public Response getAll() {
        return Response.ok(sintomaGraveUseCase.obtenerTodos()).build();
    }

    // GET /admin/sintomas-graves/vacuna/{idVacuna}
    @GET
    @Path("/vacuna/{idVacuna}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Listar síntomas graves por vacuna",
            description = "Devuelve todos los síntomas graves asociados a una vacuna específica"
    )

    @Parameter(name = "idVacuna", description = "ID de la vacuna", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Lista obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "sintomas por vacuna",
                            value = "[{\"id\":1,\"idVacuna\":1,\"nombre\":\"Miocarditis\"},{\"id\":2,\"idVacuna\":1,\"nombre\":\"Anafilaxia\"}]"
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
        return Response.ok(sintomaGraveUseCase.obtenerPorVacuna(idVacuna)).build();
    }

    // GET /admin/sintomas-graves/{id}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Obtener síntoma grave por ID",
            description = "Busca y devuelve un síntoma grave por su ID único"
    )

    @Parameter(name = "id", description = "ID del síntoma grave", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Síntoma encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "sintoma",
                            value = "{\"id\":1,\"idVacuna\":1,\"nombre\":\"Miocarditis\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Síntoma grave no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"SintomaGrave con id 99 no encontrado\"}"
                    )
            )
    )

    public Response getById(@PathParam("id") Integer id) {
        return Response.ok(sintomaGraveUseCase.obtenerPorId(id)).build();
    }

    // POST /admin/sintomas-graves
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(summary = "Crear síntoma grave",
            description = "Registra un nuevo síntoma grave asociado a una vacuna, maximo 150 caaracteres")

    @APIResponse(
            responseCode = "201",
            description = "Síntoma creado correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "sintoma creado",
                            value = "{\"id\":4,\"idVacuna\":1,\"nombre\":\"Parálisis facial\"}"
                    )
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "Datos inválidos — nombre vacío o idVacuna faltante",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "error validacion",
                            value = "{\"error\": \"El nombre del síntoma es obligatorio\"}"
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
            description = "Actualiza el nombre de un síntoma existente, vacuna asociada no cambia")

    @Parameter(name = "id", description = "ID del síntoma grave a editar", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Síntoma actualizado correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "sintoma actualizado",
                            value = "{\"id\":1,\"idVacuna\":1,\"nombre\":\"Miocarditis aguda\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Síntoma grave no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"SintomaGrave con id 99 no encontrado\"}"
                    )
            )
    )

    public Response editar(
            @PathParam("id") Integer id,
            @Valid ActualizarSintomaGraveDto dto
    ) {
        return Response.ok(sintomaGraveUseCase.actualizar(id, dto)).build();
    }

    // DELETE /admin/sintomas-graves/{id}
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(summary = "Eliminar síntoma grave",
            description = "Elimina un síntoma solo si no tiene reportes adversos asociados")

    @Parameter(name = "id", description = "ID del síntoma grave a eliminar", example = "1")

    @APIResponse(responseCode = "204", description = "Eliminado correctamente")

    @APIResponse(
            responseCode = "400",
            description = "No se puede eliminar — tiene reportes adversos asociados",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "error fk",
                            value = "{\"error\": \"No se puede eliminar — tiene 3 reporte(s) adverso(s) asociado(s)\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")


    @APIResponse(
            responseCode = "404",
            description = "Síntoma grave no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"SintomaGrave con id 99 no encontrado\"}"
                    )
            )
    )

    public Response eliminar(@PathParam("id") Integer id) {
        sintomaGraveUseCase.eliminar(id);
        return Response.noContent().build();
    }
}
