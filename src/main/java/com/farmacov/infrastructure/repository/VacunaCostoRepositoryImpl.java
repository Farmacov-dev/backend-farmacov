package com.farmacov.infrastructure.repository;

import com.farmacov.domain.models.VacunaCosto;
import com.farmacov.domain.repository.VacunaCostoRepository;
import com.farmacov.infrastructure.entities.VacunaCostoEntity;
import com.farmacov.infrastructure.mapper.VacunaCostoMapper;
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
public class VacunaCostoRepositoryImpl
        implements VacunaCostoRepository, PanacheRepositoryBase<VacunaCostoEntity, Integer> {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public VacunaCosto save(VacunaCosto vacunaCosto) {
        VacunaCostoEntity entity = VacunaCostoMapper.toEntity(vacunaCosto);
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());
        entity.setVacuna(em.getReference(VacunaEntity.class, vacunaCosto.getIdVacuna()));
        persist(entity);
        return VacunaCostoMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public List<VacunaCosto> findByIdVacuna(Integer idVacuna) {
        return find("vacuna.id", idVacuna)
                .stream()
                .map(VacunaCostoMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Optional<VacunaCosto> findCostoById(Integer id) {
        return findByIdOptional(id)
                .map(VacunaCostoMapper::toDomain);
    }

    @Override
    @Transactional
    public VacunaCosto update(VacunaCosto vacunaCosto) {
        VacunaCostoEntity entity = findByIdOptional(vacunaCosto.getId())
                .orElseThrow(() -> new NotFoundException(
                        "VacunaCosto con id " + vacunaCosto.getId() + " no encontrado"
                ));
        entity.setVacuna(em.getReference(VacunaEntity.class, vacunaCosto.getIdVacuna()));
        entity.setCostoUnitario(vacunaCosto.getCostoUnitario());
        entity.setActualizadoEn(LocalDateTime.now());
        return VacunaCostoMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void deleteCostoById(Integer id) {
        deleteById(id);
    }
}