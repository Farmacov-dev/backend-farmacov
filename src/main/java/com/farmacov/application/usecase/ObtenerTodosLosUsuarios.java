package com.farmacov.application.usecase;

import com.farmacov.domain.models.Usuarios;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped; // existe mientras el server corra
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ObtenerTodosLosUsuarios {
    @Inject
    UsuariosRepository usuariosRepository; //inyecta el contrato para habalr con la db

    public List<Usuarios> execute(){
        return  usuariosRepository.findAllUsuarios(); // delega el repo y nos devuele en forma de lista

    }

}
