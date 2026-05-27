package com.farmacov.infrastructure.csv;

import com.farmacov.infrastructure.entities.EfectoSecundarioEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class EfectoSecundarioInserter {

    @Inject
    EntityManager em;

    @Transactional(TxType.REQUIRES_NEW)
    public void insertar(EfectoSecundarioEntity entity) {
        em.persist(entity);
    }
}