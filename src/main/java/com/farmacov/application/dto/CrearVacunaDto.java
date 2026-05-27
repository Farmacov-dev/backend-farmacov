package com.farmacov.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CrearVacunaDto {

    // ID del farmaco al que pertenece — obligatorio
    @NotNull(message = "El id del fármaco es obligatorio")
    private Integer idFarmaco;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    @Size(max = 100, message = "La farmacéutica no puede superar 100 caracteres")
    private String farmaceutica;

    @Size(max = 50, message = "El tipo no puede superar 50 caracteres")
    private String tipo;

    private String descripcionGeneral;

    public CrearVacunaDto() {}

    public Integer getIdFarmaco() { return idFarmaco; }
    public void setIdFarmaco(Integer idFarmaco) { this.idFarmaco = idFarmaco; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFarmaceutica() { return farmaceutica; }
    public void setFarmaceutica(String farmaceutica) { this.farmaceutica = farmaceutica; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcionGeneral() { return descripcionGeneral; }
    public void setDescripcionGeneral(String descripcionGeneral) { this.descripcionGeneral = descripcionGeneral; }
}