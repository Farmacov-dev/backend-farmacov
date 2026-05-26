package com.farmacov.application.dto;

import java.time.LocalDateTime;

public class BitacoraResponseDto {

    private String nombreAdmin;
    private String accion;
    private LocalDateTime creadoEn;

    public BitacoraResponseDto() {}

    public BitacoraResponseDto(String nombreAdmin, String accion, LocalDateTime creadoEn) {
        this.nombreAdmin = nombreAdmin;
        this.accion = accion;
        this.creadoEn = creadoEn;
    }

    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public void setNombreAdmin(String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
}
