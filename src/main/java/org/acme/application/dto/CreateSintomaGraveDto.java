package org.acme.application.dto;

public class CreateSintomaGraveDto {
    private Integer idVacuna;
    private String nombre;

    public CreateSintomaGraveDto(){
    }

    public CreateSintomaGraveDto(Integer idVacuna, String nombre){
        this.idVacuna = idVacuna;
        this.nombre = nombre;
    }

    public Integer getIdVacuna(){
        return idVacuna;
    }

    public void setIdVacuna(Integer idVacuna){
        this.idVacuna = idVacuna;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}
