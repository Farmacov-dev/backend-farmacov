package com.farmacov.infrastructure.repository;

import com.farmacov.domain.models.Vacuna;
import com.farmacov.domain.repository.VacunaRepository;
import com.farmacov.infrastructure.entities.VacunaEntity;
import com.farmacov.infrastructure.mapper.VacunaMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class VacunaRepositoryImpl implements VacunaRepository, PanacheRepositoryBase<VacunaEntity, Integer> {

    // ---------------------------------------------------------------------------
    // GET /vacunas — catálogo completo
    // Necesita: condiciones (temperatura, tiempoAmbiente) y costos (costoUnitario)
    //
    // Por qué dos queries en lugar de un solo JOIN FETCH con ambas colecciones:
    // Hibernate lanza MultipleBagFetchException cuando se intenta hacer JOIN FETCH
    // simultáneo sobre dos colecciones List<> del mismo root entity en una sola query.
    // La solución estándar es encadenar dos queries separadas dentro de la misma
    // transacción. El persistence context (caché de 1er nivel) garantiza que la
    // segunda query enriquece las mismas instancias cargadas por la primera,
    // sin duplicar objetos en memoria.
    // ---------------------------------------------------------------------------
    @Override
    @Transactional
    public List<Vacuna> findAllVacunas() {

        // Query 1 — trae vacunas con sus condiciones de almacenamiento
        List<VacunaEntity> vacunas = getEntityManager()
                .createQuery("""
                        SELECT DISTINCT v FROM VacunaEntity v
                        LEFT JOIN FETCH v.condiciones
                        ORDER BY v.id ASC
                        """, VacunaEntity.class)
                .getResultList();

        // Query 2 — inicializa la colección de costos sobre las mismas entidades
        // El persistence context resuelve las referencias: no se crean duplicados
        if (!vacunas.isEmpty()) {
            getEntityManager()
                    .createQuery("""
                            SELECT DISTINCT v FROM VacunaEntity v
                            LEFT JOIN FETCH v.costos
                            WHERE v IN :vacunas
                            """, VacunaEntity.class)
                    .setParameter("vacunas", vacunas)
                    .getResultList();
        }

        return vacunas.stream()
                .map(VacunaMapper::toDomain)
                .collect(Collectors.toList());
    }

    // ---------------------------------------------------------------------------
    // GET /vacunas/{id} — detalle de una vacuna
    // Necesita: condiciones (temperatura, tiempoAmbiente) y efectosSecundarios (lista completa)
    //
    // Mismo patrón de dos queries: evita MultipleBagFetchException y garantiza
    // que los efectos secundarios se cargan de forma eager y ordenada
    // dentro de la misma transacción.
    // ---------------------------------------------------------------------------
    @Override
    @Transactional
    public Optional<Vacuna> findVacunaById(Integer id) {

        // Query 1 — trae la vacuna con sus condiciones
        Optional<VacunaEntity> entityOpt = getEntityManager()
                .createQuery("""
                        SELECT v FROM VacunaEntity v
                        LEFT JOIN FETCH v.condiciones
                        WHERE v.id = :id
                        """, VacunaEntity.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();

        // Query 2 — inicializa la lista completa de efectos secundarios si existe la vacuna
        entityOpt.ifPresent(v -> getEntityManager()
                .createQuery("""
                        SELECT v FROM VacunaEntity v
                        LEFT JOIN FETCH v.efectosSecundarios
                        WHERE v.id = :id
                        """, VacunaEntity.class)
                .setParameter("id", id)
                .getSingleResult());

        return entityOpt.map(VacunaMapper::toDomain);
    }

    //metodo para optimizacion de kpi
    @Override
    public long countVacunas() {
        return count();
    }

    @Override
    @Transactional
    public Vacuna saveVacuna(Vacuna vacuna) {
        VacunaEntity entity = new VacunaEntity();

        // El id es manual en vacunas — sin AUTO_INCREMENT
        entity.setId(vacuna.getIdVacuna());
        entity.setNombre(vacuna.getNombre());
        entity.setFarmaceutica(vacuna.getFarmaceutica());
        entity.setTipo(vacuna.getTipo());
        entity.setDescripcionGeneral(vacuna.getDescripcionGeneral());
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());

        // Referencia al farmaco sin cargarlo completo
        entity.setFarmaco(getEntityManager().getReference(
                com.farmacov.infrastructure.entities.FarmacoEntity.class,
                vacuna.getIdFarmaco()
        ));

        persist(entity);
        return VacunaMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public Vacuna updateVacuna(Vacuna vacuna) {
        VacunaEntity entity = findByIdOptional(vacuna.getIdVacuna())
                .orElseThrow(() -> new jakarta.ws.rs.NotFoundException(
                        "Vacuna con id " + vacuna.getIdVacuna() + " no encontrada"
                ));

        // Solo actualizamos campos editables
        // El id_farmaco no cambia — cambiar de farmaco no tiene sentido
        entity.setNombre(vacuna.getNombre());
        entity.setFarmaceutica(vacuna.getFarmaceutica());
        entity.setTipo(vacuna.getTipo());
        entity.setDescripcionGeneral(vacuna.getDescripcionGeneral());
        entity.setActualizadoEn(LocalDateTime.now());

        return VacunaMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void deleteVacunaById(Integer id) {
        // Verificamos que no tenga reportes adversos — integridad de datos
        long reportes = getEntityManager()
                .createQuery(
                        "SELECT COUNT(r) FROM ReporteAdversoEntity r WHERE r.vacuna.id = :id",
                        Long.class)
                .setParameter("id", id)
                .getSingleResult();

        if (reportes > 0) {
            throw new jakarta.ws.rs.BadRequestException(
                    "No se puede eliminar — tiene " + reportes + " reporte(s) adverso(s)"
            );
        }

        deleteById(id);
    }


    @Override
    @Transactional
    public Map<Integer, BigDecimal> findIndicesSeguridad() {
        List<Object[]> rows = getEntityManager()
                .createNativeQuery("""
                SELECT v.id, vis.indice_seguridad
                FROM vacunas v
                LEFT JOIN vista_indice_seguridad vis ON v.id = vis.id_vacuna
                """)
                .getResultList();

        return rows.stream()
                .collect(Collectors.toMap(
                        row -> ((Number) row[0]).intValue(),
                        row -> row[1] != null
                                ? BigDecimal.valueOf(((Number) row[1]).doubleValue())
                                : BigDecimal.ZERO
                ));
    }



}
