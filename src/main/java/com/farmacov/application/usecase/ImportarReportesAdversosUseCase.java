package com.farmacov.application.usecase;

import com.farmacov.application.dto.ImportResultDto;
import com.farmacov.infrastructure.csv.CsvParser;
import com.farmacov.infrastructure.csv.ReporteAdversoInserter;
import com.farmacov.infrastructure.entities.ReporteAdversoEntity;
import com.farmacov.infrastructure.entities.SintomaGraveEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApplicationScoped
// Sin @Transactional aquí — cada fila maneja su propia transacción
public class ImportarReportesAdversosUseCase {

    @Inject
    EntityManager em;

    @Inject
    ReporteAdversoInserter inserter;

    private static final Set<String> SEXOS_VALIDOS =
            Set.of("M", "F", "U");

    private static final Set<String> GRUPOS_EDAD_VALIDOS =
            Set.of("0-17", "18-29", "30-49", "50-64", "65+", "DESCONOCIDO");

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
                String idStr        = CsvParser.getCampo(fila, 0);
                String idVacunaStr  = CsvParser.getCampo(fila, 1);
                String idSintomaStr = CsvParser.getCampo(fila, 2);
                String sexo         = CsvParser.getCampo(fila, 3);
                String grupoEdad    = CsvParser.getCampo(fila, 4);
                String esGraveStr   = CsvParser.getCampo(fila, 5);
                String fechaStr     = CsvParser.getCampo(fila, 6);

                // Validaciones
                if (idStr == null) throw new IllegalArgumentException("id es obligatorio");
                if (idVacunaStr == null) throw new IllegalArgumentException("id_vacuna es obligatorio");
                if (sexo == null || !SEXOS_VALIDOS.contains(sexo))
                    throw new IllegalArgumentException("sexo inválido: '" + sexo + "'");
                if (grupoEdad == null || !GRUPOS_EDAD_VALIDOS.contains(grupoEdad))
                    throw new IllegalArgumentException("grupo_edad inválido: '" + grupoEdad + "'");
                if (fechaStr == null) throw new IllegalArgumentException("fecha_reporte es obligatoria");

                // Parsear — tolerante a decimales de Excel
                Long id = (long) Double.parseDouble(idStr);
                Integer idVacuna = (int) Double.parseDouble(idVacunaStr);
                Boolean esGrave = esGraveStr != null && esGraveStr.trim().equals("1");
                LocalDate fechaReporte = LocalDate.parse(fechaStr.trim());

                // Verificar que la vacuna existe
                VacunaEntity vacuna = em.find(VacunaEntity.class, idVacuna);
                if (vacuna == null)
                    throw new IllegalArgumentException("id_vacuna " + idVacuna + " no existe en la BD");

                // Verificar síntoma si viene
                SintomaGraveEntity sintoma = null;
                if (idSintomaStr != null) {
                    Integer idSintoma = (int) Double.parseDouble(idSintomaStr);
                    sintoma = em.find(SintomaGraveEntity.class, idSintoma);
                    if (sintoma == null)
                        throw new IllegalArgumentException("id_sintoma " + idSintoma + " no existe en la BD");
                }

                // Construir entity
                ReporteAdversoEntity entity = new ReporteAdversoEntity();
                entity.setId(id);
                entity.setVacuna(vacuna);
                entity.setSintomaGrave(sintoma);
                entity.setSexo(sexo);
                entity.setGrupoEdad(grupoEdad);
                entity.setEsGrave(esGrave);
                entity.setFechaReporte(fechaReporte);

                // Insertar en su propia transacción — si falla solo esta fila hace rollback
                inserter.insertar(entity);
                insertados++;

            } catch (NumberFormatException e) {
                errores++;
                detalles.add("Fila " + numeroFila + ": número inválido — " + e.getMessage());
            } catch (DateTimeParseException e) {
                errores++;
                detalles.add("Fila " + numeroFila + ": fecha inválida — usa formato YYYY-MM-DD");
            } catch (IllegalArgumentException e) {
                errores++;
                detalles.add("Fila " + numeroFila + ": " + e.getMessage());
            } catch (Exception e) {
                errores++;
                detalles.add("Fila " + numeroFila + ": error inesperado — " + e.getMessage());
            }
        }

        // Al terminar toda la importación — recalcula la tabla precalculada
        // Se llama siempre aunque haya errores parciales
        // Si el SP falla, la importación igual devuelve su resultado
        try {
            em.createNativeQuery("CALL sp_recalcular_resumen_sintomas()")
                    .executeUpdate();
        } catch (Exception e) {
            detalles.add("Advertencia: no se pudo recalcular el resumen de síntomas — " + e.getMessage());
        }

        ImportResultDto resultado = new ImportResultDto();
        resultado.setTotalFilas(totalFilas);
        resultado.setInsertados(insertados);
        resultado.setErrores(errores);
        resultado.setDetalles(detalles);

        return resultado;
    }
}