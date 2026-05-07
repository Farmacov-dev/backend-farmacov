package com.farmacov.domain.models;

public class Roles {
    // A
    private Integer id;
    private String nombre;
    private Boolean esAdmin;

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
}
