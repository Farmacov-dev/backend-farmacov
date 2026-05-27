package com.farmacov.application.dto;

public class HistorialKpisDto {

    private long usuariosActivos;
    private long usuariosSuspendidos;

    public HistorialKpisDto() {
    }

    public HistorialKpisDto(long usuariosActivos, long usuariosSuspendidos) {
        this.usuariosActivos = usuariosActivos;
        this.usuariosSuspendidos = usuariosSuspendidos;
    }

    public long getUsuariosActivos() {
        return usuariosActivos;
    }

    public void setUsuariosActivos(long usuariosActivos) {
        this.usuariosActivos = usuariosActivos;
    }

    public long getUsuariosSuspendidos() {
        return usuariosSuspendidos;
    }

    public void setUsuariosSuspendidos(long usuariosSuspendidos) {
        this.usuariosSuspendidos = usuariosSuspendidos;
    }
}
