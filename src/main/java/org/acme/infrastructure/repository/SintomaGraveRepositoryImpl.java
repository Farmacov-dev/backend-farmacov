package org.acme.infrastructure.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.domain.models.SintomaGrave;
import org.acme.domain.repository.SintomaGraveRepository;
import org.acme.infrastructure.entities.SintomaGraveEntity;
import org.acme.infrastructure.mapper.SintomaGraveMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SintomaGraveRepositoryImpl implements
        SintomaGraveRepository, PanacheRepositoryBase<SintomaGraveEntity,Integer> {

    @Override
    @Transactional
    public SintomaGrave save(SintomaGrave sintomaGrave){
        SintomaGraveEntity entity = SintomaGraveMapper.toEntity(sintomaGrave);
        persist(entity);
        return SintomaGraveMapper.toDomain(entity);
    }

    @Override
    public Optional<SintomaGrave> getById(Integer id){
        return findByIdOptional(id).map(SintomaGraveMapper::toDomain);
    }

    @Override
    public List<SintomaGrave> getAll(){
        return listAll().stream().map(SintomaGraveMapper::toDomain)
                .collect(Collectors.toList());
    }
}
