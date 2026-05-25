package com.farmacov.application.usecase;

import com.farmacov.domain.models.Roles;
import com.farmacov.domain.repository.RolesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ObtenerTodosLosRoles {

    @Inject
    RolesRepository rolesRepository; // inyecta el contrato para hablar con la db

    public List<Roles> execute() {
        return rolesRepository.findAllRoles(); // delega al repo y devuelve la lista
    }
}
