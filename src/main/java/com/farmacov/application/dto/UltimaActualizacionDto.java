package com.farmacov.application.dto;

import java.time.LocalDateTime;

public class UltimaActualizacionDto {

    private LocalDateTime fecha;

    public UltimaActualizacionDto() {}

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}