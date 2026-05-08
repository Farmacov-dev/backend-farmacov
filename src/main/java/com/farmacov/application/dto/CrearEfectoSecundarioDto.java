package com.farmacov.application.dto;

//import com.farmacov.infrastructure.entities.EfectoSecundarioEntity.Severidad;
import com.farmacov.domain.models.EfectoSecundario.Severidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CrearEfectoSecundarioDto {

    // id de la vacuna de la cual sale  el efecto secundario
    @NotNull(message = "El id de la vacuna es obligatorio")
    private Integer idVacuna;

    // Descripción del efecto, no puede ser vacio ni solo espacios (@NotBlank)
    // y respeta el VARCHAR(200) del schema
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "La descripción no puede superar 200 caracteres")
    private String descripcion;

    // Severidad usando el enum de la Entity, los valores validos son
    // leve, moderado, grave. Si llega otro valor, Quarkus rechaza con 400.
    @NotNull(message = "La severidad es obligatoria")
    private Severidad severidad;

    public CrearEfectoSecundarioDto() {}

    public Integer getIdVacuna() { return idVacuna; }
    public void setIdVacuna(Integer idVacuna) { this.idVacuna = idVacuna; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Severidad getSeveridad() { return severidad; }
    public void setSeveridad(Severidad severidad) { this.severidad = severidad; }
}