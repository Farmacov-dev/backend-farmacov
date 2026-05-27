package com.farmacov.application.usecase;

import com.farmacov.application.dto.ImportResultDto;
import com.farmacov.infrastructure.csv.CsvParser;
import com.farmacov.infrastructure.csv.VacunaInserter;
import com.farmacov.infrastructure.entities.FarmacoEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ImportarVacunasUseCase {

    @Inject
    EntityManager em;

    @Inject
    VacunaInserter inserter;

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
                String idStr            = CsvParser.getCampo(fila, 0);
                String idFarmacoStr     = CsvParser.getCampo(fila, 1);
                String nombre           = CsvParser.getCampo(fila, 2);
                String farmaceutica     = CsvParser.getCampo(fila, 3);
                String tipo             = CsvParser.getCampo(fila, 4);
                String descripcionGeneral = CsvParser.getCampo(fila, 5);

                if (idStr == null)
                    throw new IllegalArgumentException("id es obligatorio");
                if (idFarmacoStr == null)
                    throw new IllegalArgumentException("id_farmaco es obligatorio");
                if (nombre == null || nombre.isBlank())
                    throw new IllegalArgumentException("nombre es obligatorio");

                Integer id = (int) Double.parseDouble(idStr);
                Integer idFarmaco = (int) Double.parseDouble(idFarmacoStr);

                FarmacoEntity farmaco = em.find(FarmacoEntity.class, idFarmaco);
                if (farmaco == null)
                    throw new IllegalArgumentException("id_farmaco " + idFarmaco + " no existe en la BD");

                VacunaEntity entity = new VacunaEntity();
                entity.setId(id);
                entity.setFarmaco(farmaco);
                entity.setNombre(nombre);
                entity.setFarmaceutica(farmaceutica);
                entity.setTipo(tipo);
                entity.setDescripcionGeneral(descripcionGeneral);
                entity.setCreadoEn(LocalDateTime.now());
                entity.setActualizadoEn(LocalDateTime.now());

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