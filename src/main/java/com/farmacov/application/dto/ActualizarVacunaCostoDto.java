package com.farmacov.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ActualizarVacunaCostoDto {

    @NotNull(message = "costo unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "costo debe ser mayor a 0")
    @Digits(integer = 6, fraction = 2, message = "costo debe tener máximo 6 enteros y 2 decimales")
    private BigDecimal costoUnitario;

    public ActualizarVacunaCostoDto() {}

    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }
}