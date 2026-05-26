package com.farmacov.infrastructure.repository;

import com.farmacov.domain.models.IndiceSeguridadResult;
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
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
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

    // -------------------------------------------------------------------------
    // sp_indice_seguridad — para una vacuna específica
    // OUT p_indice es DECIMAL(5,2) en MySQL → el driver lo entrega como
    // BigDecimal, por eso registramos BigDecimal.class en lugar de Double.
    // El use case decidirá cómo convertirlo para el DTO de respuesta.
    // -------------------------------------------------------------------------
    @Override
    public IndiceSeguridadResult getIndiceSeguridad(Integer idVacuna) {
        StoredProcedureQuery q = em.createStoredProcedureQuery("sp_indice_seguridad");
        q.registerStoredProcedureParameter("p_id_vacuna", Integer.class,    ParameterMode.IN);
        q.registerStoredProcedureParameter("p_total",     Long.class,       ParameterMode.OUT);
        q.registerStoredProcedureParameter("p_graves",    Long.class,       ParameterMode.OUT);
        q.registerStoredProcedureParameter("p_indice",    java.math.BigDecimal.class, ParameterMode.OUT);
        q.setParameter("p_id_vacuna", idVacuna);
        q.execute();

        Long   total  = (Long)                  q.getOutputParameterValue("p_total");
        Long   graves = (Long)                  q.getOutputParameterValue("p_graves");
        java.math.BigDecimal indice = (java.math.BigDecimal) q.getOutputParameterValue("p_indice");

        return new IndiceSeguridadResult(idVacuna, null, total, graves, indice);
    }

    // -------------------------------------------------------------------------
    // vista_indice_seguridad — todos los índices de una vez
    // -------------------------------------------------------------------------
    @Override
    @SuppressWarnings("unchecked")
    public List<IndiceSeguridadResult> getAllIndiceSeguridad() {
        List<Object[]> rows = em
                .createNativeQuery(
                        "SELECT id_vacuna, nombre_vacuna, total_reportes, reportes_graves, indice_seguridad " +
                        "FROM vista_indice_seguridad")
                .getResultList();

        return rows.stream().map(r -> new IndiceSeguridadResult(
                ((Number) r[0]).intValue(),
                (String)  r[1],
                ((Number) r[2]).longValue(),
                r[3] != null ? ((Number) r[3]).longValue() : 0L,
                r[4] != null ? java.math.BigDecimal.valueOf(((Number) r[4]).doubleValue()) : null
        )).toList();
    }

    /// optimizaicon: implementacion de metodos optimizacion de kpis
    @Override
    public long countAll() {
        // COUNT(*) directo — MySQL devuelve solo un número
        return count();
    }

    @Override
    public long countByEsGrave(boolean esGrave) {
        // COUNT WHERE es_grave = ? — no trae objetos a memoria
        return count("esGrave", esGrave);
    }

    @Override
    @Transactional
    public long countByMesYAnio(int mes, int anio) {
        // MySQL filtra por mes y año — no viajan registros a Java
        return (long) getEntityManager()
                .createQuery(
                        "SELECT COUNT(r) FROM ReporteAdversoEntity r " +
                                "WHERE MONTH(r.fechaReporte) = :mes " +
                                "AND YEAR(r.fechaReporte) = :anio")
                .setParameter("mes", mes)
                .setParameter("anio", anio)
                .getSingleResult();
    }



    @Override
    @Transactional
    public long countByIdSintoma(Integer idSintoma) {
        return count("idSintoma", idSintoma);
    }


}
