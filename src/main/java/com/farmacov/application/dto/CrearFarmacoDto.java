package com.farmacov.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CrearFarmacoDto {

    @NotBlank(message = "El nombre del fármaco es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 50, message = "El tipo no puede superar 50 caracteres")
    private String tipo;

    // Descripción opcional
    private String descripcion;

    public CrearFarmacoDto() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}