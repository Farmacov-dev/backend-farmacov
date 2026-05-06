//paso 2 despues de hacer vacunacondicionEntity
package com.farmacov.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VacunaCondicion {

    private Integer id;

    // Solo guardamos el ID de la vacuna — el dominio no necesita
    // saber todo sobre ella, solo a cuál pertenece
    private Integer idVacuna;

    // NOT NULL en el schema — nunca será null en Java
    private BigDecimal temperatura;

    // Nullable en el schema — puede ser null si la vacuna
    // no tiene restricción de tiempo ambiente
    private BigDecimal tiempoAmbiente;

    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    public VacunaCondicion() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdVacuna() { return idVacuna; }
    public void setIdVacuna(Integer idVacuna) { this.idVacuna = idVacuna; }

    public BigDecimal getTemperatura() { return temperatura; }
    public void setTemperatura(BigDecimal temperatura) { this.temperatura = temperatura; }

    public BigDecimal getTiempoAmbiente() { return tiempoAmbiente; }
    public void setTiempoAmbiente(BigDecimal tiempoAmbiente) { this.tiempoAmbiente = tiempoAmbiente; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }

    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }
}