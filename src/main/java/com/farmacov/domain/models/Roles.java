package com.farmacov.domain.models;

import java.util.Map;

public class Roles {
    // A
    private Integer id;
    private String nombre;
    private Boolean esAdmin;
    // adicion para toggles
    private Map<String, Boolean> permisos;

    // C

    public Roles(){

    }

    // G

    public Integer getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public Boolean getEsAdmin(){
        return  esAdmin;
    }

    // adicion para toggles
    public Map<String, Boolean> getPermisos(){
        return permisos;
    }

    // S

    public void setId(Integer id){
        this.id = id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setEsAdmin(Boolean esAdmin){
        this.esAdmin = esAdmin;
    }

    //adicion para toggles
    public void setPermisos(Map<String, Boolean> permisos){this.permisos = permisos;}
}
