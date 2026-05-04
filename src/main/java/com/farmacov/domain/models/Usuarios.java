package com.farmacov.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Usuarios {

    // A
    private UUID id;
    private String firebaseUuid;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private Roles rol;
    private String departamento;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    // C

    public Usuarios() {

    }

    // G

    public UUID getId() {
        return id;
    }

    public String getFirebaseUuid() {
        return firebaseUuid;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public Roles getRol() {
        return rol;
    }

    public String getDepartamento() {
        return departamento;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    // S

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFirebaseUuid(String firebaseUuid) {
        this.firebaseUuid = firebaseUuid;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}
