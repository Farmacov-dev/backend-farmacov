package com.farmacov.infrastructure.repository;

import com.farmacov.domain.models.EfectoSecundario;
import com.farmacov.domain.models.EfectoSecundario.Severidad;
import com.farmacov.domain.repository.EfectoSecundarioRepository;
import com.farmacov.infrastructure.entities.EfectoSecundarioEntity;
import com.farmacov.infrastructure.mapper.EfectoSecundarioMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;

// @ApplicationScoped — un solo instance de este repositorio vive
// durante toda la aplicación. Quarkus lo inyecta donde se necesite con @Inject.
@ApplicationScoped
public class EfectoSecundarioRepositoryImpl
        implements EfectoSecundarioRepository, PanacheRepositoryBase<EfectoSecundarioEntity, Integer> {

    // EntityManager nos permite usar em.getReference() para construir
    // referencias a entidades relacionadas sin cargarlas completas de la BD
    @Inject
    EntityManager em;

    @Override
    @Transactional
    public EfectoSecundario save(EfectoSecundario efectoSecundario) {
        EfectoSecundarioEntity entity = EfectoSecundarioMapper.toEntity(efectoSecundario);

        // getReference() le dice a Hibernate: "confía en que esta vacuna existe,
        // no la cargues de la BD, solo usa su ID para respetar la FK"
        entity.setVacuna(em.getReference(VacunaEntity.class, efectoSecundario.getIdVacuna()));

        persist(entity);
        return EfectoSecundarioMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public List<EfectoSecundario> findByIdVacuna(Integer idVacuna) {
        // Panache entiende "vacuna.id" porque conoce la relación @ManyToOne.
        // Internamente genera: WHERE e.vacuna.id = ?
        return find("vacuna.id", idVacuna)
                .stream()
                .map(EfectoSecundarioMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Optional<EfectoSecundario> findEfectoById(Integer id) {
        // findByIdOptional es el método de Panache — no choca con nuestra interfaz
        // porque nuestro método se llama findEfectoById
        return findByIdOptional(id)
                .map(EfectoSecundarioMapper::toDomain);
    }

    @Override
    @Transactional
    public List<EfectoSecundario> findByIdVacunaAndSeveridad(Integer idVacuna, Severidad severidad) {
        // Traducimos el enum del dominio al enum de la Entity antes de filtrar,
        // porque Panache trabaja con tipos de la Entity, no del dominio
        EfectoSecundarioEntity.Severidad severidadEntity =
                EfectoSecundarioEntity.Severidad.valueOf(severidad.name());

        return find("vacuna.id = ?1 and severidad = ?2", idVacuna, severidadEntity)
                .stream()
                .map(EfectoSecundarioMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public EfectoSecundario update(EfectoSecundario efectoSecundario) {
        // Primero verificamos que el registro existe antes de intentar actualizar.
        // Si no existe, lanzamos NotFoundException con un mensaje claro.
        EfectoSecundarioEntity entity = findByIdOptional(efectoSecundario.getId())
                .orElseThrow(() -> new NotFoundException(
                        "EfectoSecundario con id " + efectoSecundario.getId() + " no encontrado"
                ));

        // Actualizamos solo los campos editables.
        // Hibernate detecta los cambios automáticamente dentro de la transacción
        // y genera el UPDATE sin necesitar un persist() explícito.
        entity.setDescripcion(efectoSecundario.getDescripcion());
        entity.setSeveridad(
                EfectoSecundarioEntity.Severidad.valueOf(efectoSecundario.getSeveridad().name())
        );

        return EfectoSecundarioMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void deleteEfectoById(Integer id) {
        // deleteById es el método de Panache — no choca porque el nuestro
        // se llama deleteEfectoById
        deleteById(id);
    }
}