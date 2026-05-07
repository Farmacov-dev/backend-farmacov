package com.farmacov.infrastructure.repository;

import com.farmacov.domain.models.VacunaCondicion;
import com.farmacov.domain.repository.VacunaCondicionRepository;
import com.farmacov.infrastructure.entities.VacunaCondicionEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import com.farmacov.infrastructure.mapper.VacunaCondicionMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VacunaCondicionRepositoryImpl
        implements VacunaCondicionRepository, PanacheRepositoryBase<VacunaCondicionEntity, Integer> {

    // EntityManager para construir referencias a entidades relacionadas
    // sin cargarlas completas de la base de datos
    @Inject
    EntityManager em;

    @Override
    @Transactional
    public VacunaCondicion save(VacunaCondicion vacunaCondicion) {
        VacunaCondicionEntity entity = VacunaCondicionMapper.toEntity(vacunaCondicion);

        // getReference() le dice a Hibernate: usa el ID de la vacuna
        // para respetar la FK sin cargar el objeto completo de la BD
        entity.setVacuna(em.getReference(VacunaEntity.class, vacunaCondicion.getIdVacuna()));

        // Los timestamps los asigna Java en el momento del INSERT —
        // no dependemos del DEFAULT de MySQL para tener control explícito
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());

        persist(entity);
        return VacunaCondicionMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public List<VacunaCondicion> findByIdVacuna(Integer idVacuna) {
        // Panache resuelve "vacuna.id" gracias a la relación @ManyToOne
        // Genera internamente: WHERE v.vacuna.id = ?
        return find("vacuna.id", idVacuna)
                .stream()
                .map(VacunaCondicionMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Optional<VacunaCondicion> findCondicionById(Integer id) {
        // findByIdOptional es de Panache — no choca con nuestra interfaz
        // porque nuestro método se llama findCondicionById
        return findByIdOptional(id)
                .map(VacunaCondicionMapper::toDomain);
    }

    @Override
    @Transactional
    public VacunaCondicion update(VacunaCondicion vacunaCondicion) {
        // Verificamos que el registro existe antes de actualizar.
        // Si no existe lanzamos NotFoundException con mensaje claro.
        VacunaCondicionEntity entity = findByIdOptional(vacunaCondicion.getId())
                .orElseThrow(() -> new NotFoundException(
                        "VacunaCondicion con id " + vacunaCondicion.getId() + " no encontrada"
                ));

        // Actualizamos los campos editables.
        // tiempoAmbiente puede llegar null — eso es válido según el schema.
        // Hibernate detecta los cambios dentro de la transacción
        // y genera el UPDATE automáticamente sin persist() explícito.
        entity.setTemperatura(vacunaCondicion.getTemperatura());
        entity.setTiempoAmbiente(vacunaCondicion.getTiempoAmbiente());
        entity.setActualizadoEn(LocalDateTime.now());

        return VacunaCondicionMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void deleteCondicionById(Integer id) {
        // deleteById es de Panache — no choca porque el nuestro
        // se llama deleteCondicionById
        deleteById(id);
    }
}