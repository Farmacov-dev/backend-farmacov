package com.farmacov.infrastructure.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    // Lee el CSV y devuelve cada fila como String[]
    // La primera línea (headers) se salta automáticamente
    public static List<String[]> parse(InputStream inputStream) throws IOException {
        List<String[]> filas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String linea;
            boolean primeraLinea = true;

            while ((linea = reader.readLine()) != null) {
                // Saltamos la línea de headers
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                // Saltamos líneas vacías
                if (linea.isBlank()) continue;

                // Separamos por coma
                String[] campos = linea.split(",", -1);
                filas.add(campos);
            }
        }

        return filas;
    }

    // Utilidad — obtiene un campo por índice de forma segura
    // Si el índice no existe o está vacío devuelve null
    public static String getCampo(String[] fila, int indice) {
        if (indice >= fila.length) return null;
        String valor = fila[indice].trim();
        return valor.isEmpty() ? null : valor;
    }
}