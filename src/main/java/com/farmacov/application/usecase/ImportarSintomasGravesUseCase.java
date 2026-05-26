package com.farmacov.application.usecase;

import com.farmacov.application.dto.ImportResultDto;
import com.farmacov.infrastructure.csv.CsvParser;
import com.farmacov.infrastructure.csv.SintomaGraveInserter;
import com.farmacov.infrastructure.entities.SintomaGraveEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ImportarSintomasGravesUseCase {

    @Inject
    EntityManager em;

    @Inject
    SintomaGraveInserter inserter;

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
                String nombre      = CsvParser.getCampo(fila, 1);

                if (idVacunaStr == null)
                    throw new IllegalArgumentException("id_vacuna es obligatorio");
                if (nombre == null || nombre.isBlank())
                    throw new IllegalArgumentException("nombre es obligatorio");
                if (nombre.length() > 150)
                    throw new IllegalArgumentException("nombre no puede superar 150 caracteres");

                Integer idVacuna = (int) Double.parseDouble(idVacunaStr);

                VacunaEntity vacuna = em.find(VacunaEntity.class, idVacuna);
                if (vacuna == null)
                    throw new IllegalArgumentException("id_vacuna " + idVacuna + " no existe en la BD");

                SintomaGraveEntity entity = new SintomaGraveEntity();
                entity.setVacuna(vacuna);
                entity.setNombre(nombre);

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