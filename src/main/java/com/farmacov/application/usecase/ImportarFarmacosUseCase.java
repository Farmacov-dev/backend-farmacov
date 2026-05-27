package com.farmacov.application.usecase;

import com.farmacov.application.dto.ImportResultDto;
import com.farmacov.infrastructure.csv.CsvParser;
import com.farmacov.infrastructure.csv.FarmacoInserter;
import com.farmacov.infrastructure.entities.FarmacoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ImportarFarmacosUseCase {

    @Inject
    FarmacoInserter inserter;

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
                String nombre      = CsvParser.getCampo(fila, 0);
                String tipo        = CsvParser.getCampo(fila, 1);
                String descripcion = CsvParser.getCampo(fila, 2);

                if (nombre == null || nombre.isBlank())
                    throw new IllegalArgumentException("nombre es obligatorio");
                if (tipo == null || tipo.isBlank())
                    throw new IllegalArgumentException("tipo es obligatorio");

                FarmacoEntity entity = new FarmacoEntity();
                entity.setNombre(nombre);
                entity.setTipo(tipo);
                entity.setDescripcion(descripcion);
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