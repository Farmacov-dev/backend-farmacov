package com.farmacov.infrastructure.repository;

import com.farmacov.domain.models.SintomaGrave;
import com.farmacov.domain.repository.SintomaGraveRepository;
import com.farmacov.infrastructure.entities.SintomaGraveEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import com.farmacov.infrastructure.mapper.SintomaGraveMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SintomaGraveRepositoryImpl implements
        SintomaGraveRepository, PanacheRepositoryBase<SintomaGraveEntity, Integer> {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public SintomaGrave save(SintomaGrave sintomaGrave) {
        SintomaGraveEntity entity = SintomaGraveMapper.toEntity(sintomaGrave);
        // Se usa un proxy administrado para no hacer un SELECT extra de la vacuna.
        entity.setVacuna(em.getReference(VacunaEntity.class, sintomaGrave.getIdVacuna()));
        persist(entity);
        return SintomaGraveMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public Optional<SintomaGrave> getById(Integer id) {
        return findByIdOptional(id).map(SintomaGraveMapper::toDomain);
    }

    @Override
    @Transactional
    public List<SintomaGrave> getAll() {
        return listAll().stream()
                .map(SintomaGraveMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SintomaGrave> findByIdVacuna(Integer idVacuna) {
        return find("vacuna.id", idVacuna)
                .stream()
                .map(SintomaGraveMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public SintomaGrave update(SintomaGrave sintomaGrave) {
        SintomaGraveEntity entity = findByIdOptional(sintomaGrave.getId())
                .orElseThrow(() -> new NotFoundException(
                        "SintomaGrave con id " + sintomaGrave.getId() + " no encontrado"
                ));

        // Solo actualizamos el nombre — la vacuna no cambia
        // La relacion con la vacuna no se toca; solo cambia el nombre visible.
        entity.setNombre(sintomaGrave.getNombre());

        return SintomaGraveMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void deleteSintomaById(Integer id) {
        deleteById(id);
    }



}
