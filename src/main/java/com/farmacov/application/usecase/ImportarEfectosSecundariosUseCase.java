package com.farmacov.application.usecase;

import com.farmacov.application.dto.ImportResultDto;
import com.farmacov.infrastructure.csv.CsvParser;
import com.farmacov.infrastructure.csv.EfectoSecundarioInserter;
import com.farmacov.infrastructure.entities.EfectoSecundarioEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class ImportarEfectosSecundariosUseCase {

    @Inject
    EntityManager em;

    @Inject
    EfectoSecundarioInserter inserter;

    private static final Set<String> SEVERIDADES_VALIDAS =
            Set.of("leve", "moderado", "grave");

    public ImportResultDto execute(InputStream csvStream) {
        List<String[]> filas;
        try {
            filas = CsvParser.parse(csvStream);
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo el archivo CSV: " + e.getMessage());
        }

        int totalFilas = filas.size();
        int insertados = 0;
        int errores = 0;
        List<String> detalles = new ArrayList<>();

        for (int i = 0; i < filas.size(); i++) {
            int numeroFila = i + 2;
            String[] fila = filas.get(i);

            try {
                String idVacunaStr = CsvParser.getCampo(fila, 0);
                String descripcion = CsvParser.getCampo(fila, 1);
                String severidad   = CsvParser.getCampo(fila, 2);

                // Validaciones
                if (idVacunaStr == null)
                    throw new IllegalArgumentException("id_vacuna es obligatorio");
                if (descripcion == null || descripcion.isBlank())
                    throw new IllegalArgumentException("descripcion es obligatoria");
                if (severidad == null || !SEVERIDADES_VALIDAS.contains(severidad.toLowerCase()))
                    throw new IllegalArgumentException(
                            "severidad inválida: '" + severidad + "' — valores válidos: leve, moderado, grave"
                    );

                Integer idVacuna = (int) Double.parseDouble(idVacunaStr);

                // Verificar que la vacuna existe
                VacunaEntity vacuna = em.find(VacunaEntity.class, idVacuna);
                if (vacuna == null)
                    throw new IllegalArgumentException("id_vacuna " + idVacuna + " no existe en la BD");

                // Construir entity
                EfectoSecundarioEntity entity = new EfectoSecundarioEntity();
                entity.setVacuna(vacuna);
                entity.setDescripcion(descripcion);
                entity.setSeveridad(
                        EfectoSecundarioEntity.Severidad.valueOf(severidad.toLowerCase())
                );

                inserter.insertar(entity);
                insertados++;

            } catch (IllegalArgumentException e) {
                errores++;
                detalles.add("Fila " + numeroFila + ": " + e.getMessage());
            } catch (Exception e) {
                errores++;
                detalles.add("Fila " + numeroFila + ": error inesperado — " + e.getMessage());
            }
        }

        ImportResultDto resultado = new ImportResultDto();
        resultado.setTotalFilas(totalFilas);
        resultado.setInsertados(insertados);
        resultado.setErrores(errores);
        resultado.setDetalles(detalles);
        return resultado;
    }
}