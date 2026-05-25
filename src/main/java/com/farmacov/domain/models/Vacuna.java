package com.farmacov.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Vacuna {

    // A — vacunas
    private Integer idVacuna;
    private String nombre;
    private String farmaceutica;
    private String tipo;
    private String descripcionGeneral;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;

    // Atributos de vacuna_condiciones
    private BigDecimal temperatura;
    private BigDecimal tiempoAmbiente;

    // Atributos de vacuna_costos
    private BigDecimal costoUnitario;

    // Atributos de efectos_secundarios — lista completa
    private List<EfectoSecundario> efectosSecundarios;


    // Atributos de farmaco
    private Integer idFarmaco;
    private String nombreFarmaco;

    // C
    public Vacuna() {}

    // G
    public Integer getIdVacuna() {
        return idVacuna;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFarmaceutica() {
        return farmaceutica;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcionGeneral() {
        return descripcionGeneral;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }

    public BigDecimal getTiempoAmbiente() {
        return tiempoAmbiente;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public List<EfectoSecundario> getEfectosSecundarios() {
        return efectosSecundarios;
    }

    public Integer getIdFarmaco() {
        return idFarmaco;
    }

    public String getNombreFarmaco() {
        return nombreFarmaco;
    }

    // S
    public void setIdVacuna(Integer idVacuna) {
        this.idVacuna = idVacuna;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFarmaceutica(String farmaceutica) {
        this.farmaceutica = farmaceutica;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcionGeneral(String descripcionGeneral) {
        this.descripcionGeneral = descripcionGeneral;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

    public void setTemperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
    }

    public void setTiempoAmbiente(BigDecimal tiempoAmbiente) {
        this.tiempoAmbiente = tiempoAmbiente;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public void setEfectosSecundarios(List<EfectoSecundario> efectosSecundarios) {
        this.efectosSecundarios = efectosSecundarios;
    }

    public void setIdFarmaco(Integer idFarmaco) {
        this.idFarmaco = idFarmaco;
    }

    public void setNombreFarmaco(String nombreFarmaco) {
        this.nombreFarmaco = nombreFarmaco;
    }
}
