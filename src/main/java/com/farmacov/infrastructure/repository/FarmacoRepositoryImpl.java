package com.farmacov.infrastructure.repository;

import com.farmacov.domain.models.Farmaco;
import com.farmacov.domain.repository.FarmacoRepository;
import com.farmacov.infrastructure.entities.FarmacoEntity;
import com.farmacov.infrastructure.mapper.FarmacoMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FarmacoRepositoryImpl
        implements FarmacoRepository, PanacheRepositoryBase<FarmacoEntity, Integer> {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public List<Farmaco> findAllFarmacos() {
        // listAll() es el método correcto de Panache — no choca con findAll()
        return listAll().stream()
                .map(FarmacoMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Optional<Farmaco> findFarmacoById(Integer id) {
        // findByIdOptional() de Panache — nombre propio no choca
        return findByIdOptional(id)
                .map(FarmacoMapper::toDomain);
    }

    @Override
    @Transactional
    public Farmaco save(Farmaco farmaco) {
        FarmacoEntity entity = FarmacoMapper.toEntity(farmaco);
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());
        persist(entity);
        return FarmacoMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public Farmaco update(Farmaco farmaco) {
        FarmacoEntity entity = findByIdOptional(farmaco.getId())
                .orElseThrow(() -> new NotFoundException(
                        "Farmaco con id " + farmaco.getId() + " no encontrado"
                ));

        entity.setNombre(farmaco.getNombre());
        entity.setTipo(farmaco.getTipo());
        entity.setDescripcion(farmaco.getDescripcion());
        entity.setActualizadoEn(LocalDateTime.now());

        return FarmacoMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void deleteFarmacoById(Integer id) {
        // Verificamos que no tenga vacunas asociadas
        long vacunasConEsteFarmaco = em.createQuery(
                        "SELECT COUNT(v) FROM VacunaEntity v WHERE v.farmaco.id = :id",
                        Long.class)
                .setParameter("id", id)
                .getSingleResult();

        if (vacunasConEsteFarmaco > 0) {
            throw new BadRequestException(
                    "No se puede eliminar — tiene " + vacunasConEsteFarmaco +
                            " vacuna(s) asociada(s)"
            );
        }

        // deleteById() es de Panache — no choca porque nuestro método
        // se llama deleteFarmacoById
        deleteById(id);
    }
}