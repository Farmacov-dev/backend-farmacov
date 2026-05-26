package com.farmacov.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class CrearRolDto {

    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombre;

    @NotNull(message = "esAdmin es obligatorio")
    private Boolean esAdmin;

    // Permisos opcionales — si no se mandan se inicializan en false
    private Map<String, Boolean> permisos;

    public CrearRolDto() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Boolean getEsAdmin() { return esAdmin; }
    public void setEsAdmin(Boolean esAdmin) { this.esAdmin = esAdmin; }

    public Map<String, Boolean> getPermisos() { return permisos; }
    public void setPermisos(Map<String, Boolean> permisos) { this.permisos = permisos; }
}