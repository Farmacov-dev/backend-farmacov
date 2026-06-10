package com.farmacov.api;

import com.farmacov.application.dto.ActualizarEfectoSecundarioDto;
import com.farmacov.application.dto.CrearEfectoSecundarioDto;
import com.farmacov.application.dto.DistribucionSeveridadDto;
import com.farmacov.application.usecase.EfectoSecundarioUseCase;
import com.farmacov.application.usecase.ObtenerDistribucionSeveridadUseCase;
import com.farmacov.application.usecase.ObtenerDistribucionSeveridadPorVacunaUseCase;
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
import org.eclipse.microprofile.openapi.annotations.tags.Tag;





@Path("/admin/efectos-secundarios")
@Tag(name = "Admin - Efectos Secundarios", description = "Gestión de efectos secundarios de vacunas")
public class EfectosSecundariosResource {

    @Inject
    EfectoSecundarioUseCase efectoSecundarioUseCase;

    @Inject
    ObtenerDistribucionSeveridadUseCase obtenerDistribucionSeveridadUseCase;

    @Inject
    ObtenerDistribucionSeveridadPorVacunaUseCase obtenerDistribucionSeveridadPorVacunaUseCase;

    // GET /admin/efectos-secundarios/vacuna/{idVacuna}
    // Obtiene todos los efectos secundarios de una vacuna
    @GET
    @Path("/vacuna/{idVacuna}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(summary = "Listar efectos secundarios por vacuna",
            description = "Obtiene todos los efectos secundarios de una vacuna específica")

    @Parameter(name = "idVacuna", description = "ID de la vacuna", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Lista obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "efectos por vacuna",
                            value = "[{\"id\":1,\"idVacuna\":1,\"descripcion\":\"Dolor en sitio de inyección\",\"severidad\":\"leve\"},{\"id\":2,\"idVacuna\":1,\"descripcion\":\"Fatiga\",\"severidad\":\"leve\"},{\"id\":3,\"idVacuna\":1,\"descripcion\":\"Miocarditis\",\"severidad\":\"grave\"}]"
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
                efectoSecundarioUseCase.obtenerPorVacuna(idVacuna)
        ).build();
    }

    // GET /admin/efectos-secundarios/{id}
    // Obtiene un efecto secundario por su ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(summary = "Obtener efecto secundario por ID")

    @Parameter(name = "id", description = "ID del efecto secundario", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Efecto secundario encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "efecto secundario",
                            value = "{\"id\":1,\"idVacuna\":1,\"descripcion\":\"Dolor en sitio de inyección\",\"severidad\":\"leve\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Efecto secundario no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"EfectoSecundario con id 99 no encontrado\"}"
                    )
            )
    )

    public Response getById(@PathParam("id") Integer id) {
        return Response.ok(
                efectoSecundarioUseCase.obtenerPorId(id)
        ).build();
    }

    // POST /admin/efectos-secundarios
    // Crea un efecto secundario nuevo
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(summary = "Crear efecto secundario",
            description = "Registra un nuevo efecto secundario asociado a una vacuna")

    @APIResponse(
            responseCode = "201",
            description = "Efecto secundario creado correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "efecto creado",
                            value = "{\"id\":4,\"idVacuna\":1,\"descripcion\":\"Fiebre alta\",\"severidad\":\"moderado\"}"
                    )
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "Datos inválidos — descripción vacía, severidad inválida o idVacuna faltante",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "error validacion",
                            value = "{\"error\": \"La severidad es obligatoria\"}"
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

    public Response crear(@Valid CrearEfectoSecundarioDto dto) {
        return Response.status(201)
                .entity(efectoSecundarioUseCase.crear(dto))
                .build();
    }

    // PUT /admin/efectos-secundarios/{id}
    // Actualiza descripción y/o severidad
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(summary = "Editar efecto secundario",
            description = "Actualiza descripción y severidad de un efecto existente")

    @Parameter(name = "id", description = "ID del efecto secundario a editar", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Efecto secundario actualizado correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "efecto actualizado",
                            value = "{\"id\":1,\"idVacuna\":1,\"descripcion\":\"Dolor leve en brazo\",\"severidad\":\"leve\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Efecto secundario no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"EfectoSecundario con id 99 no encontrado\"}"
                    )
            )
    )

    public Response editar(
            @PathParam("id") Integer id,
            @Valid ActualizarEfectoSecundarioDto dto
    ) {
        return Response.ok(
                efectoSecundarioUseCase.actualizar(id, dto)
        ).build();
    }

    // DELETE /admin/efectos-secundarios/{id}
    // Elimina solo si no tiene reportes adversos asociados
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(summary = "Eliminar efecto secundario",
            description = "Elimina un efecto secundario solo si no tiene reportes adversos asociados")

    @Parameter(name = "id", description = "ID del efecto secundario a eliminar", example = "1")

    @APIResponse(responseCode = "204", description = "Eliminado correctamente — sin contenido")

    @APIResponse(
            responseCode = "400",
            description = "No se puede eliminar, tiene reportes adversos asociados",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "error fk",
                            value = "{\"error\": \"No se puede eliminar — tiene 5 reporte(s) adverso(s) asociado(s)\"}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    @APIResponse(
            responseCode = "404",
            description = "Efecto secundario no encontrado",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "no encontrado",
                            value = "{\"error\": \"EfectoSecundario con id 99 no encontrado\"}"
                    )
            )
    )

    public Response eliminar(@PathParam("id") Integer id) {
        efectoSecundarioUseCase.eliminar(id);
        return Response.noContent().build();
    }

    // GET /dashboard/efectos-secundarios/distribucion-severidad
    // Distribución global de severidad
    @GET
    @Path("/distribucion-severidad")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(summary = "Distribución global de severidad",
            description = "Conteo de efectos por severidad en todo el sistema")

    @APIResponse(
            responseCode = "200",
            description = "Distribución obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "distribucion global",
                            value = "{\"leve\":45,\"moderado\":23,\"grave\":7}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    public Response getDistribucionSeveridad() {
        DistribucionSeveridadDto dto = obtenerDistribucionSeveridadUseCase.execute();
        return Response.ok(dto).build();
    }

    // GET /dashboard/efectos-secundarios/distribucion-severidad/{idVacuna}
    // Distribución de severidad por vacuna
    @GET
    @Path("/distribucion-severidad/{idVacuna}")
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(summary = "Distribución de severidad por vacuna",
            description = "Conteo de efectos por severidad para una vacuna específica")

    @Parameter(name = "idVacuna", description = "ID de la vacuna", example = "1")

    @APIResponse(
            responseCode = "200",
            description = "Distribución obtenida correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "distribucion por vacuna",
                            value = "{\"leve\":8,\"moderado\":3,\"grave\":2}"
                    )
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    public Response getDistribucionSeveridadPorVacuna(
            @PathParam("idVacuna") Integer idVacuna
    ) {
        DistribucionSeveridadDto dto =
                obtenerDistribucionSeveridadPorVacunaUseCase.execute(idVacuna);
        return Response.ok(dto).build();
    }
}