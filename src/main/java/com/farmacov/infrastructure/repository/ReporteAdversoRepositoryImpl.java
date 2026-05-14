package com.farmacov.infrastructure.repository;

import com.farmacov.domain.models.ReporteAdverso;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import com.farmacov.infrastructure.entities.ReporteAdversoEntity;
import com.farmacov.infrastructure.entities.SintomaGraveEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import com.farmacov.infrastructure.mapper.ReporteAdversoMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ReporteAdversoRepositoryImpl
        implements ReporteAdversoRepository, PanacheRepositoryBase<ReporteAdversoEntity, Long> {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public ReporteAdverso save(ReporteAdverso reporteAdverso) {
        ReporteAdversoEntity entity = ReporteAdversoMapper.toEntity(reporteAdverso);
        entity.setVacuna(em.getReference(VacunaEntity.class, reporteAdverso.getIdVacuna()));
        if (reporteAdverso.getIdSintoma() != null) {
            entity.setSintomaGrave(em.getReference(SintomaGraveEntity.class, reporteAdverso.getIdSintoma()));
        }
        persist(entity);
        return ReporteAdversoMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public Optional<ReporteAdverso> getById(Long id) {
        return findByIdOptional(id).map(ReporteAdversoMapper::toDomain);
    }

    @Override
    @Transactional
    public List<ReporteAdverso> getAll() {
        return listAll().stream()
                .map(ReporteAdversoMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public List<ReporteAdverso> getByIdVacuna(Integer idVacuna) {
        return find("vacuna.id", idVacuna).stream()
                .map(ReporteAdversoMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public List<ReporteAdverso> getByEsGrave(Boolean esGrave) {
        return find("esGrave", esGrave).stream()
                .map(ReporteAdversoMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public Optional<LocalDateTime> findFechaUltimaActualizacion() {
        return getEntityManager()
                .createQuery("SELECT r.creadoEn FROM ReporteAdversoEntity r ORDER BY r.id DESC",
                        LocalDateTime.class)
                .setMaxResults(1)
                .getResultStream()
                .findFirst();
    }
}
