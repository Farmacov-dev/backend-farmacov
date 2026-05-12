package com.farmacov.infrastructure.repository;

import com.farmacov.domain.models.Vacuna;
import com.farmacov.domain.repository.VacunaRepository;
import com.farmacov.infrastructure.entities.VacunaEntity;
import com.farmacov.infrastructure.mapper.VacunaMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class VacunaRepositoryImpl implements VacunaRepository, PanacheRepositoryBase<VacunaEntity, Integer> {

    @Override
    @Transactional
    public List<Vacuna> findAllVacunas() {
        return listAll()
                .stream()
                .map(VacunaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<Vacuna> findVacunaById(Integer id) {
        return findByIdOptional(id)
                .map(VacunaMapper::toDomain);
    }
}
