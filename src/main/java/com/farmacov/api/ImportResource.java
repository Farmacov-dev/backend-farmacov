package com.farmacov.api;

import com.farmacov.application.dto.ImportResultDto;
import com.farmacov.application.usecase.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;



@Path("/admin/importar")
@Tag(name = "Admin - Importación CSV",
        description = "Importación masiva de datos desde archivos CSV")
public class ImportResource {

    @Inject ImportarReportesAdversosUseCase importarReportesAdversosUseCase;
    @Inject ImportarEfectosSecundariosUseCase importarEfectosSecundariosUseCase;
    @Inject ImportarSintomasGravesUseCase importarSintomasGravesUseCase;
    @Inject ImportarVacunasUseCase importarVacunasUseCase;
    @Inject ImportarFarmacosUseCase importarFarmacosUseCase;
    @Inject ImportarVacunaCostosUseCase importarVacunaCostosUseCase;


    // POST /admin/importar/reportes-adversos
    // CSV: id,id_vacuna,id_sintoma,sexo,grupo_edad,es_grave,fecha_reporte
    @POST
    @Path("/reportes-adversos")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Importar reportes adversos",
            description = "CSV: id,id_vacuna,id_sintoma,sexo,grupo_edad,es_grave,fecha_reporte")
    @APIResponse(responseCode = "200", description = "Importación completada")
    public Response importarReportesAdversos(@RestForm("archivo") FileUpload archivo) {
        return ejecutar(archivo, importarReportesAdversosUseCase::execute);
    }

    // POST /admin/importar/efectos-secundarios
    // CSV: id_vacuna,descripcion,severidad
    @POST
    @Path("/efectos-secundarios")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Importar efectos secundarios",
            description = "CSV: id_vacuna,descripcion,severidad")
    @APIResponse(responseCode = "200", description = "Importación completada")
    public Response importarEfectosSecundarios(@RestForm("archivo") FileUpload archivo) {
        return ejecutar(archivo, importarEfectosSecundariosUseCase::execute);
    }

    // POST /admin/importar/sintomas-graves
    // CSV: id_vacuna,nombre
    @POST
    @Path("/sintomas-graves")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Importar síntomas graves",
            description = "CSV: id_vacuna,nombre")
    @APIResponse(responseCode = "200", description = "Importación completada")
    public Response importarSintomasGraves(@RestForm("archivo") FileUpload archivo) {
        return ejecutar(archivo, importarSintomasGravesUseCase::execute);
    }

    // POST /admin/importar/vacunas
    // CSV: id,id_farmaco,nombre,farmaceutica,tipo,descripcion_general
    @POST
    @Path("/vacunas")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Importar vacunas",
            description = "CSV: id,id_farmaco,nombre,farmaceutica,tipo,descripcion_general")
    @APIResponse(responseCode = "200", description = "Importación completada")
    public Response importarVacunas(@RestForm("archivo") FileUpload archivo) {
        return ejecutar(archivo, importarVacunasUseCase::execute);
    }

    // POST /admin/importar/farmacos
    // CSV: nombre,tipo,descripcion
    @POST
    @Path("/farmacos")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Importar fármacos",
            description = "CSV: nombre,tipo,descripcion")
    @APIResponse(responseCode = "200", description = "Importación completada")
    public Response importarFarmacos(@RestForm("archivo") FileUpload archivo) {
        return ejecutar(archivo, importarFarmacosUseCase::execute);
    }

    // Método reutilizable — evita duplicar el manejo de FileUpload
    private Response ejecutar(FileUpload archivo,
                              java.util.function.Function<InputStream, ImportResultDto> useCase) {
        if (archivo == null) {
            return Response.status(400)
                    .entity("{\"error\": \"No se recibió ningún archivo\"}")
                    .build();
        }
        try {
            InputStream inputStream = Files.newInputStream(archivo.uploadedFile());
            ImportResultDto resultado = useCase.apply(inputStream);
            return Response.ok(resultado).build();
        } catch (IOException e) {
            return Response.status(400)
                    .entity("{\"error\": \"Error leyendo el archivo: " + e.getMessage() + "\"}")
                    .build();
        }
    }



    // POST /admin/importar/vacuna-costos
    // CSV: id_vacuna,costo_unitario
    @POST
    @Path("/vacuna-costos")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Importar costos de vacunas",
            description = "CSV: id_vacuna,costo_unitario")
    @APIResponse(responseCode = "200", description = "Importación completada")
    public Response importarVacunaCostos(@RestForm("archivo") FileUpload archivo) {
        return ejecutar(archivo, importarVacunaCostosUseCase::execute);
    }


}