package com.farmacov.interfaces.rest;

import com.farmacov.application.dto.CrearVacunaDto;
import com.farmacov.application.dto.VacunaCatalogoResponseDto;
import com.farmacov.application.dto.VacunaDetalleResponseDto;
import com.farmacov.application.usecase.GetVacunaDetalleUseCase;
import com.farmacov.application.usecase.GetVacunasCatalogoUseCase;
import com.farmacov.application.usecase.VacunaUseCase;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;

@Path("/vacunas")
@Tag(name = "Vacunas", description = "Endpoints relacionados a vacunas")
@Produces(MediaType.APPLICATION_JSON)
public class VacunaResource {

    @Inject
    GetVacunasCatalogoUseCase getVacunasCatalogoUseCase;

    @Inject
    GetVacunaDetalleUseCase getVacunaDetalleUseCase;

    @Inject
    VacunaUseCase vacunaUseCase;

    @GET
    @Operation(summary = "Obtener todas las vacunas",
            description = "Obtiene los datos del catálogo de vacunas")
    @APIResponse(responseCode = "200",
                 description = "Vacunas obtenidas",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = "kpis",
                                    value = "[\n" +
                                            "    {\n" +
                                            "        \"costoUnitario\": 19.50,\n" +
                                            "        \"farmaceutica\": \"Pfizer-BioNTech\",\n" +
                                            "        \"idFarmaco\": 1,\n" +
                                            "        \"idVacuna\": 1,\n" +
                                            "        \"indiceSeguridad\": 66.67,\n" +
                                            "        \"nombre\": \"Comirnaty\",\n" +
                                            "        \"temperatura\": -70.0,\n" +
                                            "        \"tiempoAmbiente\": 2.0\n" +
                                            "    },\n" +
                                            "    {\n" +
                                            "        \"costoUnitario\": 25.00,\n" +
                                            "        \"farmaceutica\": \"Moderna\",\n" +
                                            "        \"idFarmaco\": 1,\n" +
                                            "        \"idVacuna\": 2,\n" +
                                            "        \"indiceSeguridad\": 66.67,\n" +
                                            "        \"nombre\": \"Spikevax\",\n" +
                                            "        \"temperatura\": -20.0,\n" +
                                            "        \"tiempoAmbiente\": 12.0\n" +
                                            "    },\n" +
                                            "    {\n" +
                                            "        \"costoUnitario\": 4.00,\n" +
                                            "        \"farmaceutica\": \"AstraZeneca\",\n" +
                                            "        \"idFarmaco\": 1,\n" +
                                            "        \"idVacuna\": 3,\n" +
                                            "        \"indiceSeguridad\": 66.67,\n" +
                                            "        \"nombre\": \"Vaxzevria\",\n" +
                                            "        \"temperatura\": 4.0,\n" +
                                            "        \"tiempoAmbiente\": 1.0\n" +
                                            "    },\n" +
                                            "    {\n" +
                                            "        \"costoUnitario\": 10.00,\n" +
                                            "        \"farmaceutica\": \"Johnson & Johnson\",\n" +
                                            "        \"idFarmaco\": 1,\n" +
                                            "        \"idVacuna\": 4,\n" +
                                            "        \"indiceSeguridad\": 66.67,\n" +
                                            "        \"nombre\": \"Janssen\",\n" +
                                            "        \"temperatura\": 4.0,\n" +
                                            "        \"tiempoAmbiente\": 1.0\n" +
                                            "    },\n" +
                                            "    {\n" +
                                            "        \"costoUnitario\": 13.60,\n" +
                                            "        \"farmaceutica\": \"Sinovac\",\n" +
                                            "        \"idFarmaco\": 1,\n" +
                                            "        \"idVacuna\": 5,\n" +
                                            "        \"indiceSeguridad\": 66.67,\n" +
                                            "        \"nombre\": \"CoronaVac\",\n" +
                                            "        \"temperatura\": 4.0,\n" +
                                            "        \"tiempoAmbiente\": 1.0\n" +
                                            "    },\n" +
                                            "    {\n" +
                                            "        \"costoUnitario\": 14.50,\n" +
                                            "        \"farmaceutica\": \"Sinopharm\",\n" +
                                            "        \"idFarmaco\": 1,\n" +
                                            "        \"idVacuna\": 6,\n" +
                                            "        \"indiceSeguridad\": 100.0,\n" +
                                            "        \"nombre\": \"Sinopharm BBIBP\",\n" +
                                            "        \"temperatura\": 4.0,\n" +
                                            "        \"tiempoAmbiente\": 1.0\n" +
                                            "    },\n" +
                                            "    {\n" +
                                            "        \"costoUnitario\": 15.00,\n" +
                                            "        \"farmaceutica\": \"Bharat Biotech\",\n" +
                                            "        \"idFarmaco\": 1,\n" +
                                            "        \"idVacuna\": 7,\n" +
                                            "        \"indiceSeguridad\": 100.0,\n" +
                                            "        \"nombre\": \"Covaxin\",\n" +
                                            "        \"temperatura\": 4.0,\n" +
                                            "        \"tiempoAmbiente\": 1.0\n" +
                                            "    },\n" +
                                            "    {\n" +
                                            "        \"costoUnitario\": 16.00,\n" +
                                            "        \"farmaceutica\": \"Novavax\",\n" +
                                            "        \"idFarmaco\": 1,\n" +
                                            "        \"idVacuna\": 8,\n" +
                                            "        \"indiceSeguridad\": 100.0,\n" +
                                            "        \"nombre\": \"Nuvaxovid\",\n" +
                                            "        \"temperatura\": 4.0,\n" +
                                            "        \"tiempoAmbiente\": 1.0\n" +
                                            "    },\n" +
                                            "    {\n" +
                                            "        \"costoUnitario\": 9.95,\n" +
                                            "        \"farmaceutica\": \"Gamaleya\",\n" +
                                            "        \"idFarmaco\": 1,\n" +
                                            "        \"idVacuna\": 9,\n" +
                                            "        \"indiceSeguridad\": 100.0,\n" +
                                            "        \"nombre\": \"Sputnik V\",\n" +
                                            "        \"temperatura\": -18.0,\n" +
                                            "        \"tiempoAmbiente\": 1.0\n" +
                                            "    },\n" +
                                            "    {\n" +
                                            "        \"costoUnitario\": 10.50,\n" +
                                            "        \"farmaceutica\": \"CanSino Biologics\",\n" +
                                            "        \"idFarmaco\": 1,\n" +
                                            "        \"idVacuna\": 10,\n" +
                                            "        \"indiceSeguridad\": 100.0,\n" +
                                            "        \"nombre\": \"Convidecia\",\n" +
                                            "        \"temperatura\": 4.0,\n" +
                                            "        \"tiempoAmbiente\": 1.0\n" +
                                            "    }\n" +
                                            "]"
                            )))
    public Response getCatalogo() {
        List<VacunaCatalogoResponseDto> catalogo = getVacunasCatalogoUseCase.execute();
        return Response.ok(catalogo).build();
    }


    @GET
    @Path("/{id}")
    @Operation (
            summary = "Vacuna Detalle",
            description = "Obtiene información adicional sobre una vacuna determinada"
    )

    @APIResponse(
            responseCode = "200",
            description = "KPIs obtenidos correctamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(
                            name = "kpis",
                            value = "{\n" +
                                    "    \"costoUnitario\": 19.50,\n" +
                                    "    \"descripcionGeneral\": \"Vacuna de ARNm que instruye a las células a producir la proteína spike del SARS-CoV-2 para generar respuesta inmune.\",\n" +
                                    "    \"distribucionSeveridad\": {\n" +
                                    "        \"leve\": 2,\n" +
                                    "        \"moderado\": 0,\n" +
                                    "        \"grave\": 1\n" +
                                    "    },\n" +
                                    "    \"efectosSecundarios\": [\n" +
                                    "        {\n" +
                                    "            \"descripcion\": \"Dolor en sitio de inyección\",\n" +
                                    "            \"severidad\": \"leve\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"descripcion\": \"Fatiga\",\n" +
                                    "            \"severidad\": \"leve\"\n" +
                                    "        },\n" +
                                    "        {\n" +
                                    "            \"descripcion\": \"Miocarditis\",\n" +
                                    "            \"severidad\": \"grave\"\n" +
                                    "        }\n" +
                                    "    ],\n" +
                                    "    \"farmaceutica\": \"Pfizer-BioNTech\",\n" +
                                    "    \"nombre\": \"Comirnaty\",\n" +
                                    "    \"temperatura\": -70.0,\n" +
                                    "    \"tiempoAmbiente\": 2.0,\n" +
                                    "    \"tipo\": \"ARNm\",\n" +
                                    "    \"totalReportes\": 0\n" +
                                    "}"
                    )
            )
    )
    public Response getDetalle(@PathParam("id") Integer id) {
        VacunaDetalleResponseDto detalle = getVacunaDetalleUseCase.execute(id);
        return Response.ok(detalle).build();
    }


    // POST /vacunas/{id}
    // El id es manual — se pasa en la URL igual que en los inserts de MySQL
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear vacuna",
            description = "El ID es manual — debe ser único y no existir previamente")
    @APIResponse(responseCode = "201", description = "Vacuna creada correctamente")
    @APIResponse(responseCode = "400", description = "Datos inválidos")
    @APIResponse(responseCode = "404", description = "Fármaco no encontrado")
    @APIResponse(responseCode = "409", description = "Ya existe una vacuna con ese ID")
    public Response crear(
            @PathParam("id") Integer id,
            @Valid CrearVacunaDto dto
    ) {
        return Response.status(201)
                .entity(vacunaUseCase.crear(id, dto))
                .build();
    }

    // PUT /vacunas/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Editar vacuna",
            description = "Actualiza nombre, farmacéutica, tipo y descripción")
    @APIResponse(responseCode = "200", description = "Vacuna actualizada")
    @APIResponse(responseCode = "404", description = "No encontrada")
    public Response editar(
            @PathParam("id") Integer id,
            @Valid CrearVacunaDto dto
    ) {
        return Response.ok(vacunaUseCase.actualizar(id, dto)).build();
    }

    // DELETE /vacunas/{id}
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar vacuna",
            description = "Elimina una vacuna solo si no tiene reportes adversos")
    @APIResponse(responseCode = "204", description = "Vacuna eliminada")
    @APIResponse(responseCode = "400", description = "Tiene reportes adversos")
    @APIResponse(responseCode = "404", description = "No encontrada")
    public Response eliminar(@PathParam("id") Integer id) {
        vacunaUseCase.eliminar(id);
        return Response.noContent().build();
    }



}
