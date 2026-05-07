package com.farmacov.infrastructure.entities;

import jakarta.persistence.*;
import org.acme.infrastructure.entities.SintomaGraveEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vacunas")
public class VacunaEntity {

    @Id
    @Column(name = "id_vacuna")
    private Integer id;

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

    @OneToMany(mappedBy = "vacuna", fetch = FetchType.LAZY)
    private List<VacunaCondicionEntity> condiciones;

    @OneToMany(mappedBy = "vacuna", fetch = FetchType.LAZY)
    private List<VacunaCostoEntity> costos;

    @OneToMany(mappedBy = "vacuna", fetch = FetchType.LAZY)
    private List<EfectoSecundarioEntity> efectosSecundarios;

    @OneToMany(mappedBy = "vacuna", fetch = FetchType.LAZY)
    private List<SintomaGraveEntity> sintomasGraves;

    // C
    public VacunaEntity() {}

    // G
    public Integer getId() {
        return id;
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

    public List<VacunaCondicionEntity> getCondiciones() {
        return condiciones;
    }

    public List<VacunaCostoEntity> getCostos() {
        return costos;
    }

    public List<EfectoSecundarioEntity> getEfectosSecundarios() {
        return efectosSecundarios;
    }

    public List<SintomaGraveEntity> getSintomasGraves() {
        return sintomasGraves;
    }

    // S
    public void setIdVacuna(Integer id) {
        this.id = id;
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

    public void setCondiciones(List<VacunaCondicionEntity> condiciones) {
        this.condiciones = condiciones;
    }

    public void setCostos(List<VacunaCostoEntity> costos) {
        this.costos = costos;
    }

    public void setEfectosSecundarios(List<EfectoSecundarioEntity> efectosSecundarios) {
        this.efectosSecundarios = efectosSecundarios;
    }

    public void setSintomasGraves(List<SintomaGraveEntity> sintomasGraves) {
        this.sintomasGraves = sintomasGraves;
    }
}
