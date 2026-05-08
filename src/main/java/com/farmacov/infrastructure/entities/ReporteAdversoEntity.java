package com.farmacov.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes_adversos")
public class ReporteAdversoEntity {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vacuna", nullable = false)
    private VacunaEntity vacuna;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_sintoma")
    private SintomaGraveEntity sintomaGrave;

    @Column(name = "sexo", nullable = false, length = 1)
    private String sexo;

    @Column(name = "grupo_edad", nullable = false, length = 20)
    private String grupoEdad;

    @Column(name = "es_grave", nullable = false)
    private Boolean esGrave;

    @Column(name = "fecha_reporte", nullable = false)
    private LocalDate fechaReporte;

    @Column(name = "creado_en", insertable = false, updatable = false)
    private LocalDateTime creadoEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VacunaEntity getVacuna() {
        return vacuna;
    }

    public void setVacuna(VacunaEntity vacuna) {
        this.vacuna = vacuna;
    }

    public SintomaGraveEntity getSintomaGrave() {
        return sintomaGrave;
    }

    public void setSintomaGrave(SintomaGraveEntity sintomaGrave) {
        this.sintomaGrave = sintomaGrave;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getGrupoEdad() {
        return grupoEdad;
    }

    public void setGrupoEdad(String grupoEdad) {
        this.grupoEdad = grupoEdad;
    }

    public Boolean getEsGrave() {
        return esGrave;
    }

    public void setEsGrave(Boolean esGrave) {
        this.esGrave = esGrave;
    }

    public LocalDate getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDate fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
}
