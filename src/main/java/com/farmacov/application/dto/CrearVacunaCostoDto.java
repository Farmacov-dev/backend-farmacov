package com.farmacov.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CrearVacunaCostoDto {

    @NotNull(message = "id de vacuna es obligatorio")
    private Integer idVacuna;

    @NotNull(message = "costo unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "costo debe ser mayor a 0")
    @Digits(integer = 6, fraction = 2, message = "costo debe tener max 6 enteros y 2 decimales")
    private BigDecimal costoUnitario;

    public CrearVacunaCostoDto() {}

    public Integer getIdVacuna() { return idVacuna; }
    public void setIdVacuna(Integer idVacuna) { this.idVacuna = idVacuna; }

    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }
}