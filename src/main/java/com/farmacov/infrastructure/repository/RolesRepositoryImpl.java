package com.farmacov.infrastructure.repository;

import com.farmacov.domain.repository.RolesRepository;
import com.farmacov.domain.models.Roles;
import com.farmacov.infrastructure.entities.RolesEntity;
import com.farmacov.infrastructure.mapper.RolesMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped // este objeto existe mientras el servidor esta corriendo
public class RolesRepositoryImpl implements RolesRepository, PanacheRepositoryBase<RolesEntity, Integer> {
    // implementa el contrato y panache nos regala metodos para interactuar con la db

    @Override
    public List<Roles> findAllRoles() {
        return listAll()  // panache trae todos los rolesentity de la db
                .stream() // los convierte en flujo para transformarlos
                .map(RolesMapper::toDomain) // convierte cada enditad a modelo
                .collect(Collectors.toList()); // junta el flujo en una ñista
    }

    @Override
    public Optional<Roles> findRoleById(Integer id) {
        return findByIdOptional(id)  // panache busca por id, devuelve Optional<RolesEntity>
                .map(RolesMapper::toDomain); // convierte cada enditad a modelo si es que  existe
    }

    @Transactional
    @Override
    public Roles saveRole(Roles rol) {
        RolesEntity entity = RolesMapper.toEntity(rol); // convierte modelo a entidad
        persist(entity); // panache guarda en la db
        return RolesMapper.toDomain(entity); // devuelve el rol guardado como modelo
    }

}