package com.farmacov.application.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ActualizarVacunaCondicionDto {

    // Al actualizar no se cambia la vacuna asociada —
    // solo se corrigen temperatura y/o tiempo ambiente

    @NotNull(message = "La temperatura es obligatoria")
    @DecimalMin(value = "-999.9", message = "La temperatura mínima permitida es -999.9")
    @DecimalMax(value = "9999.9", message = "La temperatura máxima permitida es 9999.9")
    @Digits(integer = 4, fraction = 1, message = "La temperatura debe tener máximo 4 enteros y 1 decimal")
    private BigDecimal temperatura;

    // tiempoAmbiente sigue siendo opcional al actualizar —
    // mandar null es válido y borrará el valor existente en la BD
    @DecimalMin(value = "0.0", inclusive = false, message = "El tiempo ambiente debe ser mayor a 0")
    @DecimalMax(value = "9999.9", message = "El tiempo ambiente máximo permitido es 9999.9")
    @Digits(integer = 4, fraction = 1, message = "El tiempo ambiente debe tener máximo 4 enteros y 1 decimal")
    private BigDecimal tiempoAmbiente;

    public ActualizarVacunaCondicionDto() {}

    public BigDecimal getTemperatura() { return temperatura; }
    public void setTemperatura(BigDecimal temperatura) { this.temperatura = temperatura; }

    public BigDecimal getTiempoAmbiente() { return tiempoAmbiente; }
    public void setTiempoAmbiente(BigDecimal tiempoAmbiente) { this.tiempoAmbiente = tiempoAmbiente; }
}