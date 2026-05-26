package com.farmacov.application.dto;

import java.util.List;

public class ImportResultDto {

    private int totalFilas;
    private int insertados;
    private int errores;
    // Detalle de cada error — "Fila 45: id_vacuna 99 no existe"
    private List<String> detalles;

    public ImportResultDto() {}

    public int getTotalFilas() { return totalFilas; }
    public void setTotalFilas(int totalFilas) { this.totalFilas = totalFilas; }

    public int getInsertados() { return insertados; }
    public void setInsertados(int insertados) { this.insertados = insertados; }

    public int getErrores() { return errores; }
    public void setErrores(int errores) { this.errores = errores; }

    public List<String> getDetalles() { return detalles; }
    public void setDetalles(List<String> detalles) { this.detalles = detalles; }
}