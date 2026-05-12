package com.farmacov.api;

import com.farmacov.application.usecase.ObtenerTodosLosUsuarios;
import com.farmacov.domain.models.Usuarios;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/usuarios")
@ApplicationScoped
public class UsuariosResource {
    @Inject
    ObtenerTodosLosUsuarios obtenerTodosLosUsuarios; // inyectamos el use case

    @GET // metodo
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<Usuarios> usuarios = obtenerTodosLosUsuarios.execute();// llamamos al use case que llama al repo que va a la db
        return  Response.ok(usuarios).build(); // devuelve una respuesta en http + lista en json
    }

}
