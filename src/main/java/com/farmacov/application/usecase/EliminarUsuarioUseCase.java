package com.farmacov.application.usecase;
import com.farmacov.domain.repository.UsuariosRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class EliminarUsuarioUseCase {

    @Inject
    UsuariosRepository usuariosRepository;

    public void execute(UUID id){
        usuariosRepository.deleteUsuario(id);
    }

}
