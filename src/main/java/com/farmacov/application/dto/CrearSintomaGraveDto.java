package com.farmacov.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CrearSintomaGraveDto {

    @NotNull(message = "El id de la vacuna es obligatorio")
    private Integer idVacuna;

    @NotBlank(message = "El nombre del síntoma es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    private String nombre;

    public CrearSintomaGraveDto() {}

    public Integer getIdVacuna() { return idVacuna; }
    public void setIdVacuna(Integer idVacuna) { this.idVacuna = idVacuna; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}