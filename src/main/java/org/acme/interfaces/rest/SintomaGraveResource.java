package org.acme.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.application.dto.CreateSintomaGraveDto;
import org.acme.application.usecase.CreateSintomaGraveUseCase;
import org.acme.domain.models.SintomaGrave;

@Path("/sintomas-graves")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SintomaGraveResource {
    private final CreateSintomaGraveUseCase createSintomaGraveUseCase;

    @Inject
    public SintomaGraveResource(CreateSintomaGraveUseCase createSintomaGraveUseCase){
        this.createSintomaGraveUseCase = createSintomaGraveUseCase;
    }

    @POST
    public Response createSintomaGrave(CreateSintomaGraveDto dto){
        SintomaGrave sintomaGrave = createSintomaGraveUseCase.execute(dto);
        return Response.ok(sintomaGrave).build();
    }
}
