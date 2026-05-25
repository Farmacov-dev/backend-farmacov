package com.farmacov.api;
import com.farmacov.application.dto.ActualizarUsuarioDto;
import com.farmacov.application.usecase.ActualizarUsuarioUseCase;
import com.farmacov.application.usecase.EliminarUsuarioUseCase;
import com.farmacov.application.usecase.ObtenerTodosLosUsuarios;
import com.farmacov.domain.models.Usuarios;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/usuarios")
@ApplicationScoped
public class UsuariosResource {
    @Inject
    ObtenerTodosLosUsuarios obtenerTodosLosUsuarios; // inyectamos el use case

    @Inject
    ActualizarUsuarioUseCase actualizarUsuarioUseCase;

    @Inject
    EliminarUsuarioUseCase eliminarUsuarioUseCase;

    @GET // metodo
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<Usuarios> usuarios = obtenerTodosLosUsuarios.execute();// llamamos al use case que llama al repo que va a la db
        return  Response.ok(usuarios).build(); // devuelve una respuesta en http + lista en json
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuario(@PathParam("id") UUID id, @Valid ActualizarUsuarioDto dto) {
        // el use case arma el modelo, llama al repo y devuelve el usuario actualizado
        Usuarios actualizado = actualizarUsuarioUseCase.execute(id, dto);
        return Response.ok(actualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarUsuario(@PathParam("id") UUID id) {
        // el use case delega al repo, no devuelve nada — 204 es el estándar REST para borrado
        eliminarUsuarioUseCase.execute(id);
        return Response.noContent().build();
    }

}
