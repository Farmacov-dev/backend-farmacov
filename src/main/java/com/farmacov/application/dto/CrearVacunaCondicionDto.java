package com.farmacov.application.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CrearVacunaCondicionDto {

    // ID de la vacuna a la que pertenece esta condición de almacenamiento
    @NotNull(message = "El id de la vacuna es obligatorio")
    private Integer idVacuna;

    // Temperatura de almacenamiento requerida — NOT NULL en el schema.
    // DECIMAL(5,1) — máximo 4 enteros y 1 decimal, ej: 9999.9
    // Los rangos reales de temperatura de vacunas van de -80°C a +25°C
    // pero dejamos el rango amplio para no bloquear casos edge.
    @NotNull(message = "La temperatura es obligatoria")
    @DecimalMin(value = "-999.9", message = "La temperatura mínima permitida es -999.9")
    @DecimalMax(value = "9999.9", message = "La temperatura máxima permitida es 9999.9")
    @Digits(integer = 4, fraction = 1, message = "La temperatura debe tener máximo 4 enteros y 1 decimal")
    private BigDecimal temperatura;

    // Tiempo ambiente en horas — nullable en el schema.
    // No lleva @NotNull porque es opcional.
    // Si se envía, debe respetar el DECIMAL(5,1) del schema.
    @DecimalMin(value = "0.0", inclusive = false, message = "El tiempo ambiente debe ser mayor a 0")
    @DecimalMax(value = "9999.9", message = "El tiempo ambiente máximo permitido es 9999.9")
    @Digits(integer = 4, fraction = 1, message = "El tiempo ambiente debe tener máximo 4 enteros y 1 decimal")
    private BigDecimal tiempoAmbiente;

    public CrearVacunaCondicionDto() {}

    public Integer getIdVacuna() { return idVacuna; }
    public void setIdVacuna(Integer idVacuna) { this.idVacuna = idVacuna; }

    public BigDecimal getTemperatura() { return temperatura; }
    public void setTemperatura(BigDecimal temperatura) { this.temperatura = temperatura; }

    public BigDecimal getTiempoAmbiente() { return tiempoAmbiente; }
    public void setTiempoAmbiente(BigDecimal tiempoAmbiente) { this.tiempoAmbiente = tiempoAmbiente; }
}