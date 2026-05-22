package com.farmacov.application.dto;
import java.util.Map;

public class UsuarioResponseDto {

    private String email;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String departamento;
    private String rol;
    private boolean esAdmin;    // adicion para toggle y ver pantallas de admin
    private Map<String, Boolean> permisos;    //adicion para toggle

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

    // adiciones para toggle
    public boolean isEsAdmin() { return esAdmin; }
    public void setEsAdmin(boolean esAdmin) { this.esAdmin = esAdmin; }

    public Map<String, Boolean> getPermisos() { return permisos; }
    public void setPermisos(Map<String, Boolean> permisos) { this.permisos = permisos; }
}