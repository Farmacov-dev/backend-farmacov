package com.farmacov.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Bitacora {

    // A

    private Long id;
    private UUID idAdmin;
    private AccionEnum accion;
    private UUID idUsuarioAfectado;
    private LocalDateTime creadoEn;

    // Enum

    public enum AccionEnum {
        CREATE,
        UPDATE,
        DELETE
    }

    // C

    public Bitacora() {
    }

    public Bitacora(UUID idAdmin, AccionEnum accion, UUID idUsuarioAfectado, LocalDateTime creadoEn) {
        validateIdAdmin(idAdmin);
        validateAccion(accion);
        validateIdUsuarioAfectado(idUsuarioAfectado);
        validateCreadoEn(creadoEn);
        this.idAdmin = idAdmin;
        this.accion = accion;
        this.idUsuarioAfectado = idUsuarioAfectado;
        this.creadoEn = creadoEn;
    }

    // Validation

    private void validateIdAdmin(UUID idAdmin) {
        if (idAdmin == null) {
            throw new IllegalArgumentException("idAdmin no puede ser nulo");
        }
    }

    private void validateAccion(AccionEnum accion) {
        if (accion == null) {
            throw new IllegalArgumentException("accion no puede ser nula");
        }
    }

    // idUsuarioAfectado puede ser null cuando el usuario fue eliminado (accion=DELETE)
    private void validateIdUsuarioAfectado(UUID idUsuarioAfectado) {
    }

    private void validateCreadoEn(LocalDateTime creadoEn) {
        if (creadoEn == null) {
            throw new IllegalArgumentException("creadoEn no puede ser nulo");
        }
    }

    // G

    public Long getId() {
        return id;
    }

    public UUID getIdAdmin() {
        return idAdmin;
    }

    public AccionEnum getAccion() {
        return accion;
    }

    public UUID getIdUsuarioAfectado() {
        return idUsuarioAfectado;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    // S

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdAdmin(UUID idAdmin) {
        validateIdAdmin(idAdmin);
        this.idAdmin = idAdmin;
    }

    public void setAccion(AccionEnum accion) {
        validateAccion(accion);
        this.accion = accion;
    }

    public void setIdUsuarioAfectado(UUID idUsuarioAfectado) {
        validateIdUsuarioAfectado(idUsuarioAfectado);
        this.idUsuarioAfectado = idUsuarioAfectado;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        validateCreadoEn(creadoEn);
        this.creadoEn = creadoEn;
    }
}
