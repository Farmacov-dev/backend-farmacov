package com.farmacov.api;

import com.farmacov.application.dto.CreateSintomaGraveDto;
import com.farmacov.application.dto.SintomaGraveResponseDto;
import com.farmacov.application.usecase.CreateSintomaGraveUseCase;
import com.farmacov.domain.models.SintomaGrave;
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

@Path("/sintomas-graves")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SintomaGraveResource {

    @Inject
    CreateSintomaGraveUseCase createSintomaGraveUseCase;

    @POST
    public Response crear(CreateSintomaGraveDto dto) {
        SintomaGrave creado = createSintomaGraveUseCase.execute(dto);
        return Response.created(URI.create("/sintomas-graves/" + creado.getId()))
                .entity(toResponse(creado))
                .build();
    }

    @GET
    public List<SintomaGraveResponseDto> listar() {
        return createSintomaGraveUseCase.getAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @GET
    @Path("/{id}")
    public SintomaGraveResponseDto obtenerPorId(@PathParam("id") Integer id) {
        return toResponse(createSintomaGraveUseCase.getById(id));
    }

    private SintomaGraveResponseDto toResponse(SintomaGrave sintomaGrave) {
        return new SintomaGraveResponseDto(
                sintomaGrave.getId(),
                sintomaGrave.getIdVacuna(),
                sintomaGrave.getNombre()
        );
    }
}
