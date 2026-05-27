package com.farmacov.infrastructure.csv;

import com.farmacov.infrastructure.entities.VacunaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class VacunaInserter {

    @Inject
    EntityManager em;

    @Transactional(TxType.REQUIRES_NEW)
    public void insertar(VacunaEntity entity) {
        if (em.find(VacunaEntity.class, entity.getId()) != null) {
            throw new IllegalArgumentException(
                    "id " + entity.getId() + " ya existe en la BD"
            );
        }
        em.persist(entity);
    }
}