package com.farmacov.application.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ActualizarUsuarioDto {

    @NotBlank(message = "El departamento es obligatorio")
    private String departamento;

    @NotNull(message = "El rol es obligatorio")
    private Integer idRol;

    // S y G depa
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    // S y G Rol
    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }

}
