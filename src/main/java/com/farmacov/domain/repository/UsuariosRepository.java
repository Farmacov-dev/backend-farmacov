package com.farmacov.domain.repository;

import com.farmacov.domain.models.Usuarios; // modelo
import java.util.List; // devuelve listas
import java.util.Optional; // puede existir
import java.util.UUID;

public interface UsuariosRepository {
    List<Usuarios> findAllUsuarios(); // trae todos los usuarios de la db
    Optional<Usuarios> findUsuarioById(UUID id); // busca uno por id
    Optional<Usuarios> findUsuarioByFirebaseUuid(String firebaseUuid); // busca por uuid de firebase
    Optional<Usuarios> findUsuarioByCorreo(String correo); // busca por correo
    Usuarios saveUsuario(Usuarios usuario); // guarda el usuario y lo devuelve
    long countByEstado(String estado);
}
