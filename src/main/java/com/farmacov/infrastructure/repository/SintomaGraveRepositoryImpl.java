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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SintomaGraveRepositoryImpl implements
        SintomaGraveRepository, PanacheRepositoryBase<SintomaGraveEntity, Integer> {

    @Inject
    EntityManager em;  //agregado

    @Override
    @Transactional
    public SintomaGrave save(SintomaGrave sintomaGrave) {
        SintomaGraveEntity entity = SintomaGraveMapper.toEntity(sintomaGrave);
        // agregado getReference para respetar la FK
        entity.setVacuna(em.getReference(VacunaEntity.class, sintomaGrave.getIdVacuna()));
        persist(entity);
        return SintomaGraveMapper.toDomain(entity);
    }

    @Override
    @Transactional //agregado
    public Optional<SintomaGrave> getById(Integer id) {
        return findByIdOptional(id).map(SintomaGraveMapper::toDomain);
    }

    @Override
    @Transactional //agregado
    public List<SintomaGrave> getAll() {
        return listAll().stream()
                .map(SintomaGraveMapper::toDomain)
                .collect(Collectors.toList());
    }
}