package com.farmacov.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Vacuna {


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

    private String descripcionEfecto;
    private String severidadEfecto;

    private String nombreSintomaGrave;


    public Vacuna() {}

    public Vacuna(
            Integer idVacuna,
            String nombre,
            String farmaceutica,
            String tipo,
            String descripcionGeneral,
            LocalDateTime creadoEn,
            LocalDateTime actualizadoEn,
            BigDecimal temperatura,
            BigDecimal tiempoAmbiente,
            BigDecimal costoUnitario,
            String descripcionEfecto,
            String severidadEfecto,
            String nombreSintomaGrave
    ) {
        this.idVacuna = idVacuna;
        this.nombre = nombre;
        this.farmaceutica = farmaceutica;
        this.tipo = tipo;
        this.descripcionGeneral = descripcionGeneral;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
        this.temperatura = temperatura;
        this.tiempoAmbiente = tiempoAmbiente;
        this.costoUnitario = costoUnitario;
        this.descripcionEfecto = descripcionEfecto;
        this.severidadEfecto = severidadEfecto;
        this.nombreSintomaGrave = nombreSintomaGrave;
    }

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

    public String getDescripcionEfecto() {
        return descripcionEfecto;
    }

    public String getSeveridadEfecto() {
        return severidadEfecto;
    }

    public String getNombreSintomaGrave() {
        return nombreSintomaGrave;
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

    public void setDescripcionEfecto(String descripcionEfecto) {
        this.descripcionEfecto = descripcionEfecto;
    }

    public void setSeveridadEfecto(String severidadEfecto) {
        this.severidadEfecto = severidadEfecto;
    }

    public void setNombreSintomaGrave(String nombreSintomaGrave) {
        this.nombreSintomaGrave = nombreSintomaGrave;
    }
}
