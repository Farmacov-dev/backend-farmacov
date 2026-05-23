package com.farmacov.domain.models;

import java.math.BigDecimal;

/**
 * Resultado de dominio para el índice de seguridad de una vacuna.
 *
 * Contiene los datos crudos devueltos por sp_indice_seguridad /
 * vista_indice_seguridad.  El dominio no sabe nada de DTOs ni de
 * capas superiores — la conversión a IndiceSeguridadDto es
 * responsabilidad de la capa de aplicación (use cases).
 */
public class IndiceSeguridadResult {

    private Integer    idVacuna;
    private String     nombreVacuna;
    private Long       totalReportes;
    private Long       reportesGraves;

    // BigDecimal para preservar la precisión DECIMAL(5,2) del SP.
    // El use case puede convertirlo a Double antes de exponerlo en el DTO.
    private BigDecimal indiceSeguridad;

    public IndiceSeguridadResult() {}

    public IndiceSeguridadResult(Integer idVacuna, String nombreVacuna,
                                 Long totalReportes, Long reportesGraves,
                                 BigDecimal indiceSeguridad) {
        this.idVacuna        = idVacuna;
        this.nombreVacuna    = nombreVacuna;
        this.totalReportes   = totalReportes;
        this.reportesGraves  = reportesGraves;
        this.indiceSeguridad = indiceSeguridad;
    }

    // G
    public Integer    getIdVacuna()        { return idVacuna; }
    public String     getNombreVacuna()    { return nombreVacuna; }
    public Long       getTotalReportes()   { return totalReportes; }
    public Long       getReportesGraves()  { return reportesGraves; }
    public BigDecimal getIndiceSeguridad() { return indiceSeguridad; }

    // S
    public void setIdVacuna(Integer idVacuna)               { this.idVacuna        = idVacuna; }
    public void setNombreVacuna(String nombreVacuna)        { this.nombreVacuna    = nombreVacuna; }
    public void setTotalReportes(Long totalReportes)        { this.totalReportes   = totalReportes; }
    public void setReportesGraves(Long reportesGraves)      { this.reportesGraves  = reportesGraves; }
    public void setIndiceSeguridad(BigDecimal indiceSeguridad) { this.indiceSeguridad = indiceSeguridad; }
}
