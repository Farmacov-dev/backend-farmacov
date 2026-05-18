
package com.farmacov.application.dto;

public class IndiceSeguridadDto {
    private Integer idVacuna;
    private String  nombreVacuna;
    private Long    totalReportes;
    private Long    reportesGraves;
    private Double  indiceSeguridad;

    public IndiceSeguridadDto() {}

    public IndiceSeguridadDto(Integer idVacuna, String nombreVacuna,
                           Long totalReportes, Long reportesGraves, Double indiceSeguridad) {
        this.idVacuna        = idVacuna;
        this.nombreVacuna    = nombreVacuna;
        this.totalReportes   = totalReportes;
        this.reportesGraves  = reportesGraves;
        this.indiceSeguridad = indiceSeguridad;
    }

    public Integer getIdVacuna()        { return idVacuna; }
    public String  getNombreVacuna()    { return nombreVacuna; }
    public Long    getTotalReportes()   { return totalReportes; }
    public Long    getReportesGraves()  { return reportesGraves; }
    public Double  getIndiceSeguridad() { return indiceSeguridad; }
}