package com.farmacov.application.dto;

public class UsuarioResponseDto {

    private String email;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String departamento;
    private String rol;

    public UsuarioResponseDto() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}