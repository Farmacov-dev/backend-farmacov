package com.farmacov.infrastructure.repository;

import com.farmacov.domain.models.Anotacion;
import com.farmacov.domain.repository.AnotacionRepository;
import com.farmacov.infrastructure.entities.AnotacionEntity;
import com.farmacov.infrastructure.entities.UsuariosEntity;
import com.farmacov.infrastructure.mapper.AnotacionMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class AnotacionRepositoryImpl implements AnotacionRepository, PanacheRepositoryBase<AnotacionEntity, Integer> {
    @Inject
    EntityManager em;

    @Override
    @Transactional
    public Anotacion save(Anotacion anotacion){
        AnotacionEntity entity = AnotacionMapper.toEntity(anotacion);
        // getReference valida la FK sin cargar la entidad completa del usuario.
        entity.setUsuario(em.getReference(UsuariosEntity.class, anotacion.getIdUsuario()));
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());
        persist(entity);
        return AnotacionMapper.toDomain(entity);
    }

    @Override
    public Optional<Anotacion> findAnotacionById(Integer id){
        return findByIdOptional(id).map(AnotacionMapper::toDomain);
    }

    @Override
    public List<Anotacion> findAllAnotaciones(){
        // Las anotaciones mas recientes se muestran primero.
        return find("ORDER BY creadoEn DESC").stream().map(AnotacionMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Anotacion> findByUsuarioId(UUID idUsuario){
        return find("usuario.id", idUsuario).stream().map(AnotacionMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Anotacion update(Anotacion anotacion){
        AnotacionEntity entity = findByIdOptional(anotacion.getId())
                .orElseThrow(() -> new NotFoundException(
                        "Anotacion con id " + anotacion.getId() + " no encontrada"
                ));
        // La anotacion puede reasignarse a otro usuario, asi que la FK se refresca tambien.
        entity.setUsuario(em.getReference(UsuariosEntity.class, anotacion.getIdUsuario()));
        entity.setDashboardReferencia(anotacion.getDashboardReferencia());
        entity.setTitulo(anotacion.getTitulo());
        entity.setObservaciones(anotacion.getObservaciones());
        entity.setActualizadoEn(LocalDateTime.now());

        return AnotacionMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void deleteAnotacionById(Integer id){
        deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarPorUsuario(UUID idUsuario) {
        em.createQuery("DELETE FROM AnotacionEntity a WHERE a.usuario.id = :id")
                .setParameter("id", idUsuario)
                .executeUpdate();
    }

}
