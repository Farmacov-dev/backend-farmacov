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
        // idUsuarioAfectado puede ser null en acciones DELETE (el usuario ya no existe)
        if (bitacora.getIdUsuarioAfectado() != null) {
            entity.setUsuarioAfectado(getEntityManager().getReference(UsuariosEntity.class, bitacora.getIdUsuarioAfectado()));
        }
        persist(entity);
    }

    @Override
    public List<Bitacora> obtenerTodos() {
        return find("ORDER BY creadoEn DESC")
                .stream()
                .map(BitacoraMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminarPorAdmin(UUID idAdmin) {
        // borra solo las entradas donde el usuario eliminado era el admin que realizó la acción
        delete("admin.id = ?1", idAdmin);
    }

    @Override
    @Transactional
    public void eliminarPorAfectado(UUID idUsuario) {
        // id_usuario_afectado es NOT NULL en la DB, no se puede nullificar: se eliminan las filas
        delete("usuarioAfectado.id = ?1", idUsuario);
    }
}
