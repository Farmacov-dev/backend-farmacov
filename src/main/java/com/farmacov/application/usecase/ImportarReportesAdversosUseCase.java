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
public class ImportarReportesAdversosUseCase {
    // Cada fila corre en su propia transaccion para que un error no cancele toda la importacion.

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

                // Validaciones basicas antes de parsear para reportar errores por fila.
                if (idStr == null) throw new IllegalArgumentException("id es obligatorio");
                if (idVacunaStr == null) throw new IllegalArgumentException("id_vacuna es obligatorio");
                if (sexo == null || !SEXOS_VALIDOS.contains(sexo))
                    throw new IllegalArgumentException("sexo invÃ¡lido: '" + sexo + "'");
                if (grupoEdad == null || !GRUPOS_EDAD_VALIDOS.contains(grupoEdad))
                    throw new IllegalArgumentException("grupo_edad invÃ¡lido: '" + grupoEdad + "'");
                if (fechaStr == null) throw new IllegalArgumentException("fecha_reporte es obligatoria");

                // El parseo tolera numeros exportados por Excel como decimales.
                Long id = (long) Double.parseDouble(idStr);
                Integer idVacuna = (int) Double.parseDouble(idVacunaStr);
                Boolean esGrave = esGraveStr != null && esGraveStr.trim().equals("1");
                LocalDate fechaReporte = LocalDate.parse(fechaStr.trim());

                // La vacuna debe existir antes de poder persistir el reporte.
                VacunaEntity vacuna = em.find(VacunaEntity.class, idVacuna);
                if (vacuna == null)
                    throw new IllegalArgumentException("id_vacuna " + idVacuna + " no existe en la BD");

                // El sintoma es opcional, pero si viene tambien debe existir.
                SintomaGraveEntity sintoma = null;
                if (idSintomaStr != null) {
                    Integer idSintoma = (int) Double.parseDouble(idSintomaStr);
                    sintoma = em.find(SintomaGraveEntity.class, idSintoma);
                    if (sintoma == null)
                        throw new IllegalArgumentException("id_sintoma " + idSintoma + " no existe en la BD");
                }

                // Se construye la entity respetando las relaciones y claves foraneas.
                ReporteAdversoEntity entity = new ReporteAdversoEntity();
                entity.setId(id);
                entity.setVacuna(vacuna);
                entity.setSintomaGrave(sintoma);
                entity.setSexo(sexo);
                entity.setGrupoEdad(grupoEdad);
                entity.setEsGrave(esGrave);
                entity.setFechaReporte(fechaReporte);

                // Insercion aislada: una fila mala no debe abortar toda la importacion.
                inserter.insertar(entity);
                insertados++;

            } catch (NumberFormatException e) {
                errores++;
                detalles.add("Fila " + numeroFila + ": nÃºmero invÃ¡lido â€” " + e.getMessage());
            } catch (DateTimeParseException e) {
                errores++;
                detalles.add("Fila " + numeroFila + ": fecha invÃ¡lida â€” usa formato YYYY-MM-DD");
            } catch (IllegalArgumentException e) {
                errores++;
                detalles.add("Fila " + numeroFila + ": " + e.getMessage());
            } catch (Exception e) {
                errores++;
                detalles.add("Fila " + numeroFila + ": error inesperado â€” " + e.getMessage());
            }
        }

        // Se recalcula el resumen precalculado despues de la importacion, aunque haya errores parciales.
        try {
            em.createNativeQuery("CALL sp_recalcular_resumen_sintomas()")
                    .executeUpdate();
        } catch (Exception e) {
            detalles.add("Advertencia: no se pudo recalcular el resumen de sÃ­ntomas â€” " + e.getMessage());
        }

        ImportResultDto resultado = new ImportResultDto();
        resultado.setTotalFilas(totalFilas);
        resultado.setInsertados(insertados);
        resultado.setErrores(errores);
        resultado.setDetalles(detalles);

        return resultado;
    }
}
