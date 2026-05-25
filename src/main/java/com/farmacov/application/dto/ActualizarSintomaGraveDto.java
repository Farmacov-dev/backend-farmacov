package com.farmacov.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ActualizarSintomaGraveDto {

    // Solo se puede actualizar el nombre
    // La vacuna asociada no cambia
    @NotBlank(message = "El nombre del síntoma es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    private String nombre;

    public ActualizarSintomaGraveDto() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}