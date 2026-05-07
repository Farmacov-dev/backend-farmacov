package com.farmacov.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VacunaCosto {

    private Integer id;
    private Integer idVacuna;
    private BigDecimal costoUnitario;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    public VacunaCosto() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdVacuna() { return idVacuna; }
    public void setIdVacuna(Integer idVacuna) { this.idVacuna = idVacuna; }

    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }

    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }
}