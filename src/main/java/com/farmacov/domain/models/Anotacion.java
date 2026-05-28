package com.farmacov.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Anotacion {
    private Integer id;
    // Only the user UUID is stored here; the domain stays decoupled from JPA entities.
    private UUID idUsuario;
    private String dashboardReferencia;
    private String titulo;
    private String observaciones;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    public Anotacion(){
    }

    public Integer getId(){
        return id;
    }

    public UUID getIdUsuario(){
        return idUsuario;
    }

    public String getDashboardReferencia() {
        return dashboardReferencia;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getObservaciones(){
        return observaciones;
    }

    public LocalDateTime getCreadoEn(){
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn(){
        return actualizadoEn;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setIdUsuario(UUID idUsuario){
        this.idUsuario = idUsuario;
    }

    public void setDashboardReferencia(String dashboardReferencia) {
        this.dashboardReferencia = dashboardReferencia;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setObservaciones(String observaciones){
        this.observaciones = observaciones;
    }

    public void setCreadoEn(LocalDateTime creadoEn){
        this.creadoEn = creadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn){
        this.actualizadoEn = actualizadoEn;
    }
}
