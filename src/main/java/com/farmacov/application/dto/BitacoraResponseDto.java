package com.farmacov.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class BitacoraResponseDto {

    private UUID adminId;
    private String nombreAdmin;
    private String accion;
    // null cuando el usuario afectado fue eliminado (accion=DELETE)
    private String nombreUsuarioAfectado;
    private LocalDateTime creadoEn;

    public BitacoraResponseDto() {}

    public BitacoraResponseDto(UUID adminId, String nombreAdmin, String accion, String nombreUsuarioAfectado, LocalDateTime creadoEn) {
        this.adminId = adminId;
        this.nombreAdmin = nombreAdmin;
        this.accion = accion;
        this.nombreUsuarioAfectado = nombreUsuarioAfectado;
        this.creadoEn = creadoEn;
    }

    public UUID getAdminId() { return adminId; }
    public void setAdminId(UUID adminId) { this.adminId = adminId; }

    public String getNombreAdmin() { return nombreAdmin; }
    public void setNombreAdmin(String nombreAdmin) { this.nombreAdmin = nombreAdmin; }

    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }

    public String getNombreUsuarioAfectado() { return nombreUsuarioAfectado; }
    public void setNombreUsuarioAfectado(String nombreUsuarioAfectado) { this.nombreUsuarioAfectado = nombreUsuarioAfectado; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}
