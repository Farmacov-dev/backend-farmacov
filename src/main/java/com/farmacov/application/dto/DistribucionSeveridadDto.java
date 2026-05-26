package com.farmacov.application.dto;

public class DistribucionSeveridadDto {

    private Long leve;
    private Long moderado;
    private Long grave;

    public DistribucionSeveridadDto() {}

    public Long getLeve() { return leve; }
    public void setLeve(Long leve) { this.leve = leve; }

    public Long getModerado() { return moderado; }
    public void setModerado(Long moderado) { this.moderado = moderado; }

    public Long getGrave() { return grave; }
    public void setGrave(Long grave) { this.grave = grave; }
}
