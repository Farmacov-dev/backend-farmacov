package com.farmacov.infrastructure.repository;

import com.farmacov.domain.repository.UsuariosRepository;
import com.farmacov.domain.models.Usuarios;
import com.farmacov.infrastructure.entities.RolesEntity;
import com.farmacov.infrastructure.entities.UsuariosEntity;
import com.farmacov.infrastructure.mapper.UsuariosMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuariosRepositoryImpl implements UsuariosRepository, PanacheRepositoryBase<UsuariosEntity, UUID> {

    @Override
    public List<Usuarios> findAllUsuarios() {
        return listAll()  // panache trae todos los usuariosentity de la db
                .stream() // los convierte en flujo para transformarlos
                .map(UsuariosMapper::toDomain) // convierte cada entidad de usarios a modelo
                .collect(Collectors.toList()); // junta el flujo en una lista
    }

    @Override
    public Optional<Usuarios> findUsuarioById(UUID id) {
        return findByIdOptional(id)  // panache busca por id
                .map(UsuariosMapper::toDomain); // convierte entidad a modelo
    }

    @Override
    public Optional<Usuarios> findUsuarioByFirebaseUuid(String firebaseUuid) {
        return find("firebaseUuid", firebaseUuid)  // panache busca por firebaseUuid
                .firstResultOptional() // devuelve el primero si existe
                .map(UsuariosMapper::toDomain);// convierte entidad a modelo si existe
    }

    @Override
    public Optional<Usuarios> findUsuarioByCorreo(String correo) {
        return find("correo", correo)// panache busca por correo
                .firstResultOptional()// devuelve el primero si existe
                .map(UsuariosMapper::toDomain);
    }

    @Transactional
    @Override
    public Usuarios saveUsuario(Usuarios usuario) {
        UsuariosEntity entity = UsuariosMapper.toEntity(usuario); // convierte modelo a entidad
        RolesEntity rol = getEntityManager().getReference(RolesEntity.class, usuario.getRol().getId()); // obtiene referencia del rol existente en la db sin traerlo completo
        entity.setRol(rol); // asigna el rol que hibernate ya conoce
        persist(entity); // panache guarda en la db
        return UsuariosMapper.toDomain(entity); // devuelve el usuario guardado como modelo
    }

    @Transactional // si algo falla , se revierte
    @Override // cumple contrato
    public Usuarios updateUsuario(Usuarios usuario) {
        // usa un metodo de panache para buscar la UUID en la DB
        UsuariosEntity entity = findByIdOptional(usuario.getId())
                .orElseThrow(() -> new NotFoundException( // si no existe se lana un 404
                        "Usuario con id " + usuario.getId() + " no encontrado" // mensaje de fracaso
                ));
        // actulizar solo los campos editables, hibernate detecta los cambios + genera el cambio sin uso del persist
        entity.setDepartamento(usuario.getDepartamento());

        // se usa el getreference para que hibernate use el id para la fk sin cargar el objeto completo de la db
        entity.setRol(getEntityManager().getReference(RolesEntity.class, usuario.getRol().getId()));
        entity.setActualizadoEn(LocalDateTime.now());

        return UsuariosMapper.toDomain(entity);
    }

    @Transactional
    @Override
    public void deleteUsuario(UUID id) {
        // delete es de panache asi que queda en corto
        deleteById(id);
    }
}
