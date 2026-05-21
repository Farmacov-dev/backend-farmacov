package com.farmacov.domain.repository;
import com.farmacov.domain.models.Roles; // modelo
import java.util.List; // devuelve listas
import java.util.Optional; // puede existir

public interface RolesRepository {
    List<Roles> findAllRoles(); // trae todos los roles de la db
    Optional<Roles> findRoleById(Integer id); // busca uno por id
    Roles saveRole(Roles rol); // guarda el rol y lo devuelve
    Roles updateRole(Roles rol);
}
