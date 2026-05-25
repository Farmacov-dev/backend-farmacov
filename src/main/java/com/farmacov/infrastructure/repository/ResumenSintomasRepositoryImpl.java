package com.farmacov.infrastructure.repository;

import com.farmacov.application.dto.ResumenSintomasDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class ResumenSintomasRepositoryImpl {

    @Inject
    EntityManager em;

    @SuppressWarnings("unchecked")
    public List<ResumenSintomasDto> filtrar(Integer idVacuna, String sexo,
                                            String grupoEdad, Boolean esGrave, Integer limit) {
        StringBuilder sql = new StringBuilder(
                "SELECT id_vacuna, nombre_vacuna, id_sintoma, nombre_sintoma, " +
                        "sexo, grupo_edad, es_grave, total FROM resumen_sintomas WHERE 1=1"
        );

        if (idVacuna  != null) sql.append(" AND id_vacuna = :idVacuna");
        if (sexo      != null) sql.append(" AND sexo = :sexo");
        if (grupoEdad != null) sql.append(" AND grupo_edad = :grupoEdad");
        if (esGrave   != null) sql.append(" AND es_grave = :esGrave");

        sql.append(" ORDER BY total DESC");
        if (limit != null) sql.append(" LIMIT :limit");

        var query = em.createNativeQuery(sql.toString());

        if (idVacuna  != null) query.setParameter("idVacuna", idVacuna);
        if (sexo      != null) query.setParameter("sexo", sexo);
        if (grupoEdad != null) query.setParameter("grupoEdad", grupoEdad);
        if (esGrave   != null) query.setParameter("esGrave", esGrave);
        if (limit     != null) query.setParameter("limit", limit);


        List<Object[]> rows = query.getResultList();
        return rows.stream().map(r -> new ResumenSintomasDto(
                ((Number) r[0]).intValue(),
                (String)  r[1],
                r[2] != null ? ((Number) r[2]).intValue() : null,
                (String)  r[3],
                String.valueOf(r[4]),
                String.valueOf(r[5]),
                (Boolean) r[6],
                ((Number) r[7]).longValue()
        )).toList();
    }
}