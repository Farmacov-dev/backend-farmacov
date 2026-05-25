package com.farmacov.infrastructure.csv;

import com.farmacov.infrastructure.entities.ReporteAdversoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class ReporteAdversoInserter {

    @Inject
    EntityManager em;

    // REQUIRES_NEW — cada llamada abre su propia transacción independiente
    // Si falla, solo esta fila hace rollback — no afecta a las demás
    @Transactional(TxType.REQUIRES_NEW)
    public void insertar(ReporteAdversoEntity entity) {
        // Verificamos si el id ya existe antes de insertar
        if (em.find(ReporteAdversoEntity.class, entity.getId()) != null) {
            throw new IllegalArgumentException(
                    "id " + entity.getId() + " ya existe en la BD"
            );
        }
        em.persist(entity);
    }
}