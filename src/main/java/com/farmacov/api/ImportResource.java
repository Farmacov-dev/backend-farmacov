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
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;



@Path("/admin/importar")
@Tag(name = "Admin - Importación CSV",
        description = "Importación de datos desde archivos CSV")
public class ImportResource {

    @Inject ImportarReportesAdversosUseCase importarReportesAdversosUseCase;
    @Inject ImportarEfectosSecundariosUseCase importarEfectosSecundariosUseCase;
    @Inject ImportarSintomasGravesUseCase importarSintomasGravesUseCase;
    @Inject ImportarVacunasUseCase importarVacunasUseCase;
    @Inject ImportarFarmacosUseCase importarFarmacosUseCase;
    @Inject ImportarVacunaCostosUseCase importarVacunaCostosUseCase;

    private static final String EJEMPLO_RESULTADO =
            "{\"totalFilas\":6,\"insertados\":5,\"errores\":1,\"detalles\":[\"Fila 4: id_vacuna 999 no existe en la BD\"]}";

    private static final String EJEMPLO_ERROR_ARCHIVO =
            "{\"error\": \"No se recibió ningún archivo\"}";



    // POST /admin/importar/reportes-adversos
    // CSV: id,id_vacuna,id_sintoma,sexo,grupo_edad,es_grave,fecha_reporte
    @POST
    @Path("/reportes-adversos")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Importar reportes adversos desde CSV",
            description = "Formato CSV requerido: id,id_vacuna,id_sintoma,sexo,grupo_edad,es_grave,fecha_reporte. " +
                    "Sexo válido: M, F, U. Grupo edad válido: 0-17, 18-29, 30-49, 50-64, 65+, DESCONOCIDO. " +
                    "Fecha formato: YYYY-MM-DD. id_sintoma es opcional. " +
                    "Al terminar recalcula automáticamente la tabla de resumen de síntomas"
    )

    @APIResponse(
            responseCode = "200",
            description = "Importación completada — revisar insertados y errores en la respuesta",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "resultado", value = EJEMPLO_RESULTADO)
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "No se recibió archivo o el archivo no pudo leerse",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "error", value = EJEMPLO_ERROR_ARCHIVO)
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")


    public Response importarReportesAdversos(@RestForm("archivo") FileUpload archivo) {
        return ejecutar(archivo, importarReportesAdversosUseCase::execute);
    }

    // POST /admin/importar/efectos-secundarios
    // CSV: id_vacuna,descripcion,severidad
    @POST
    @Path("/efectos-secundarios")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Importar efectos secundarios desde CSV",
            description = "Formato CSV requerido: id_vacuna,descripcion,severidad. " +
                    "Severidad válida: leve, moderado, grave. " +
                    "El id es AUTO_INCREMENT — no se incluye en el CSV"
    )

    @APIResponse(
            responseCode = "200",
            description = "Importación completada — revisar insertados y errores en la respuesta",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "resultado", value = EJEMPLO_RESULTADO)
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "No se recibió archivo o el archivo no pudo leerse",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "error", value = EJEMPLO_ERROR_ARCHIVO)
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")


    public Response importarEfectosSecundarios(@RestForm("archivo") FileUpload archivo) {
        return ejecutar(archivo, importarEfectosSecundariosUseCase::execute);
    }


    // POST /admin/importar/sintomas-graves
    // CSV: id_vacuna,nombre
    @POST
    @Path("/sintomas-graves")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Importar síntomas graves desde CSV",
            description = "Formato CSV requerido: id_vacuna,nombre. " +
                    "El id es AUTO_INCREMENT — no se incluye en el CSV. " +
                    "nombre máximo 150 caracteres"
    )


    @APIResponse(
            responseCode = "200",
            description = "Importación completada — revisar insertados y errores en la respuesta",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "resultado", value = EJEMPLO_RESULTADO)
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "No se recibió archivo o el archivo no pudo leerse",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "error", value = EJEMPLO_ERROR_ARCHIVO)
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

    public Response importarSintomasGraves(@RestForm("archivo") FileUpload archivo) {
        return ejecutar(archivo, importarSintomasGravesUseCase::execute);
    }




    // POST /admin/importar/vacunas
    // CSV: id,id_farmaco,nombre,farmaceutica,tipo,descripcion_general
    @POST
    @Path("/vacunas")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Importar vacunas desde CSV",
            description = "Formato CSV requerido: id,id_farmaco,nombre,farmaceutica,tipo,descripcion_general. " +
                    "El id es manual — debe ser único y no existir previamente. " +
                    "id_farmaco debe existir en la tabla farmaco"
    )

    @APIResponse(
            responseCode = "200",
            description = "Importación completada — revisar insertados y errores en la respuesta",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "resultado", value = EJEMPLO_RESULTADO)
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "No se recibió archivo o el archivo no pudo leerse",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "error", value = EJEMPLO_ERROR_ARCHIVO)
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")


    public Response importarVacunas(@RestForm("archivo") FileUpload archivo) {
        return ejecutar(archivo, importarVacunasUseCase::execute);
    }

    // POST /admin/importar/farmacos
    // CSV: nombre,tipo,descripcion
    @POST
    @Path("/farmacos")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    @Operation(
            summary = "Importar fármacos desde CSV",
            description = "Formato CSV requerido: nombre,tipo,descripcion. " +
                    "El id es AUTO_INCREMENT — no se incluye en el CSV. " +
                    "descripcion es opcional"
    )

    @APIResponse(
            responseCode = "200",
            description = "Importación completada — revisar insertados y errores en la respuesta",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "resultado", value = EJEMPLO_RESULTADO)
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "No se recibió archivo o el archivo no pudo leerse",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "error", value = EJEMPLO_ERROR_ARCHIVO)
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")

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

    @Operation(
            summary = "Importar costos de vacunas desde CSV",
            description = "Formato CSV requerido: id_vacuna,costo_unitario. " +
                    "El id es AUTO_INCREMENT — no se incluye en el CSV. " +
                    "costo_unitario debe ser mayor a 0. " +
                    "Acepta decimales de Excel como 299.99"
    )

    @APIResponse(
            responseCode = "200",
            description = "Importación completada — revisar insertados y errores en la respuesta",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "resultado", value = EJEMPLO_RESULTADO)
            )
    )

    @APIResponse(
            responseCode = "400",
            description = "No se recibió archivo o el archivo no pudo leerse",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(name = "error", value = EJEMPLO_ERROR_ARCHIVO)
            )
    )

    @APIResponse(responseCode = "401", description = "No autorizado — token inválido o expirado")


    public Response importarVacunaCostos(@RestForm("archivo") FileUpload archivo) {
        return ejecutar(archivo, importarVacunaCostosUseCase::execute);
    }


}