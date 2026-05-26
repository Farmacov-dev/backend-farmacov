package com.farmacov.application.usecase;

import com.farmacov.application.dto.ImportResultDto;
import com.farmacov.infrastructure.csv.CsvParser;
import com.farmacov.infrastructure.csv.VacunaCostoInserter;
import com.farmacov.infrastructure.entities.VacunaCostoEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ImportarVacunaCostosUseCase {

    @Inject
    EntityManager em;

    @Inject
    VacunaCostoInserter inserter;

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
                String idVacunaStr     = CsvParser.getCampo(fila, 0);
                String costoUnitarioStr = CsvParser.getCampo(fila, 1);

                // Validaciones
                if (idVacunaStr == null)
                    throw new IllegalArgumentException("id_vacuna es obligatorio");
                if (costoUnitarioStr == null)
                    throw new IllegalArgumentException("costo_unitario es obligatorio");

                Integer idVacuna = (int) Double.parseDouble(idVacunaStr);
                BigDecimal costoUnitario = new BigDecimal(costoUnitarioStr.trim());

                // Validar que el costo sea positivo
                if (costoUnitario.compareTo(BigDecimal.ZERO) <= 0)
                    throw new IllegalArgumentException("costo_unitario debe ser mayor a 0");

                // Verificar que la vacuna existe
                VacunaEntity vacuna = em.find(VacunaEntity.class, idVacuna);
                if (vacuna == null)
                    throw new IllegalArgumentException(
                            "id_vacuna " + idVacuna + " no existe en la BD"
                    );

                VacunaCostoEntity entity = new VacunaCostoEntity();
                entity.setVacuna(vacuna);
                entity.setCostoUnitario(costoUnitario);
                entity.setCreadoEn(LocalDateTime.now());
                entity.setActualizadoEn(LocalDateTime.now());

                inserter.insertar(entity);
                insertados++;

            } catch (NumberFormatException e) {
                errores++;
                detalles.add("Fila " + numeroFila + ": costo_unitario inválido — " + e.getMessage());
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