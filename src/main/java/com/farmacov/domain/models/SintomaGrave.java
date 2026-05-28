package com.farmacov.domain.models;

public class SintomaGrave {
    private Integer id;
    private Integer idVacuna;
    private String nombre;

    public SintomaGrave() {}

    public SintomaGrave(Integer id, Integer idVacuna, String nombre) {
        this.id = id;
        this.idVacuna = idVacuna;
        this.nombre = nombre;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    // El dominio guarda solo el id de vacuna, no la entidad completa.
    public Integer getIdVacuna() { return idVacuna; }
    public void setIdVacuna(Integer idVacuna) { this.idVacuna = idVacuna; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
