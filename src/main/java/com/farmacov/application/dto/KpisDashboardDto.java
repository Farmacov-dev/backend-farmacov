package com.farmacov.application.dto;

public class KpisDashboardDto {

    private long totalVacunas;
    private long totalReportes;
    private long reportesEsteMes;
    private long totalReportesGraves;
    private double porcentajeReportesGraves;

    public KpisDashboardDto() {}

    public long getTotalVacunas() { return totalVacunas; }
    public void setTotalVacunas(long totalVacunas) { this.totalVacunas = totalVacunas; }

    public long getTotalReportes() { return totalReportes; }
    public void setTotalReportes(long totalReportes) { this.totalReportes = totalReportes; }

    public long getReportesEsteMes() { return reportesEsteMes; }
    public void setReportesEsteMes(long reportesEsteMes) { this.reportesEsteMes = reportesEsteMes; }

    public long getTotalReportesGraves() { return totalReportesGraves; }
    public void setTotalReportesGraves(long totalReportesGraves) { this.totalReportesGraves = totalReportesGraves; }

    public double getPorcentajeReportesGraves() { return porcentajeReportesGraves; }
    public void setPorcentajeReportesGraves(double porcentajeReportesGraves) { this.porcentajeReportesGraves = porcentajeReportesGraves; }
}