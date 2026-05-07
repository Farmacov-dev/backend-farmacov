package com.farmacov.api;

import com.farmacov.application.dto.CrearReporteAdversoDto;
import com.farmacov.application.dto.ReporteAdversoResponseDto;
import com.farmacov.application.service.ReporteAdversoService;
import com.farmacov.domain.models.ReporteAdverso;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/reportes-adversos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReporteAdversoResource {

    @Inject
    ReporteAdversoService reporteAdversoService;

    @POST
    public Response crear(CrearReporteAdversoDto dto) {
        ReporteAdverso creado = reporteAdversoService.crear(dto);
        return Response.created(URI.create("/reportes-adversos/" + creado.getId()))
                .entity(toResponse(creado))
                .build();
    }

    @GET
    public List<ReporteAdversoResponseDto> listar() {
        return reporteAdversoService.getAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @GET
    @Path("/{id}")
    public ReporteAdversoResponseDto obtenerPorId(@PathParam("id") Long id) {
        return toResponse(reporteAdversoService.getById(id));
    }

    private ReporteAdversoResponseDto toResponse(ReporteAdverso reporteAdverso) {
        return new ReporteAdversoResponseDto(
                reporteAdverso.getId(),
                reporteAdverso.getIdVacuna(),
                reporteAdverso.getIdSintoma(),
                reporteAdverso.getSexo().name(),
                reporteAdverso.getGrupoEdad().getValue(),
                reporteAdverso.getEsGrave(),
                reporteAdverso.getFechaReporte(),
                reporteAdverso.getCreadoEn()
        );
    }
}
