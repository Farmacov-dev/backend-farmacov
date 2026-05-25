package com.farmacov.infrastructure.csv;

import com.farmacov.infrastructure.entities.FarmacoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class FarmacoInserter {

    @Inject
    EntityManager em;

    @Transactional(TxType.REQUIRES_NEW)
    public void insertar(FarmacoEntity entity) {
        em.persist(entity);
    }
}