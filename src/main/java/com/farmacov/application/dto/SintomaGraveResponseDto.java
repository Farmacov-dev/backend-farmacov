package com.farmacov.application.dto;

public class SintomaGraveResponseDto {
    private Integer id;
    private Integer idVacuna;
    private String nombre;

    public SintomaGraveResponseDto() {
    }

    public SintomaGraveResponseDto(Integer id, Integer idVacuna, String nombre) {
        this.id = id;
        this.idVacuna = idVacuna;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdVacuna() {
        return idVacuna;
    }

    public void setIdVacuna(Integer idVacuna) {
        this.idVacuna = idVacuna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
