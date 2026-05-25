package com.farmacov.infrastructure.repository;

import com.farmacov.domain.models.Bitacora;
import com.farmacov.domain.repository.BitacoraRepository;
import com.farmacov.infrastructure.entities.BitacoraEntity;
import com.farmacov.infrastructure.entities.UsuariosEntity;
import com.farmacov.infrastructure.mapper.BitacoraMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class BitacoraRepositoryImpl implements BitacoraRepository, PanacheRepositoryBase<BitacoraEntity, Long> {

    @Override
    @Transactional
    public void registrar(Bitacora bitacora) {
        BitacoraEntity entity = BitacoraMapper.toEntity(bitacora);
        entity.setAdmin(getEntityManager().getReference(UsuariosEntity.class, bitacora.getIdAdmin()));
        entity.setUsuarioAfectado(getEntityManager().getReference(UsuariosEntity.class, bitacora.getIdUsuarioAfectado()));
        persist(entity);
    }

    @Override
    public List<Bitacora> obtenerTodos() {
        return find("ORDER BY creadoEn DESC")
                .stream()
                .map(BitacoraMapper::toDomain)
                .collect(Collectors.toList());
    }
}
