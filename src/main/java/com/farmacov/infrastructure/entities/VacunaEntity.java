package com.farmacov.infrastructure.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacunas")
public class VacunaEntity {

    @Id
    @Column(name = "id_vacuna")
    private Integer idVacuna;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "farmaceutica", length = 100)
    private String farmaceutica;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "descripcion_general", columnDefinition = "TEXT")
    private String descripcionGeneral;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @OneToOne(mappedBy = "vacuna", fetch = FetchType.LAZY)
    private VacunaCondicionEntityReferencia condicion;

    @OneToOne(mappedBy = "vacuna", fetch = FetchType.LAZY)
    private VacunaCostoEntityReferencia costo;

    @OneToOne(mappedBy = "vacuna", fetch = FetchType.LAZY)
    private EfectoSecundarioEntityReferencia efectoSecundario;

    @OneToOne(mappedBy = "vacuna", fetch = FetchType.LAZY)
    private SintomaGraveEntityReferencia sintomaGrave;

    // C
    public VacunaEntity() {}

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

    public VacunaCondicionEntityReferencia getCondicion() {
        return condicion;
    }

    public VacunaCostoEntityReferencia getCosto() {
        return costo;
    }

    public EfectoSecundarioEntityReferencia getEfectoSecundario() {
        return efectoSecundario;
    }

    public SintomaGraveEntityReferencia getSintomaGrave() {
        return sintomaGrave;
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

    public void setCondicion(VacunaCondicionEntityReferencia condicion) {
        this.condicion = condicion;
    }

    public void setCosto(VacunaCostoEntityReferencia costo) {
        this.costo = costo;
    }

    public void setEfectoSecundario(EfectoSecundarioEntityReferencia efectoSecundario) {
        this.efectoSecundario = efectoSecundario;
    }

    public void setSintomaGrave(SintomaGraveEntityReferencia sintomaGrave) {
        this.sintomaGrave = sintomaGrave;
    }
}
