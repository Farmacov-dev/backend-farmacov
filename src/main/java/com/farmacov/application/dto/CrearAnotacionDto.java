package com.farmacov.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CrearAnotacionDto {
    @NotNull(message = "idUsuario es obligatorio")
    private UUID idUsuario;

    // Identifica a que dashboard o vista pertenece la anotacion.
    @Size(max = 150, message = "dashboardReferencia no puede superar 150 caracteres")
    private String dashboardReferencia;

    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 255, message = " El titulo no puede superar 255 caracteres")
    private String titulo;

    private String observaciones;

    public CrearAnotacionDto(){
    }

    public UUID getIdUsuario(){
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario){
        this.idUsuario = idUsuario;
    }

    public String getDashboardReferencia(){
        return dashboardReferencia;
    }

    public void setDashboardReferencia(String dashboardReferencia){
        this.dashboardReferencia = dashboardReferencia;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getObservaciones(){
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}
