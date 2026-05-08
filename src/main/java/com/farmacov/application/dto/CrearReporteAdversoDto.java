package com.farmacov.application.dto;

import com.farmacov.domain.models.ReporteAdverso;

import java.time.LocalDate;

public class CrearReporteAdversoDto {
    private Long id;
    private Integer idVacuna;
    private Integer idSintoma;
    private ReporteAdverso.Sexo sexo;
    private String grupoEdad;
    private Boolean esGrave;
    private LocalDate fechaReporte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdVacuna() {
        return idVacuna;
    }

    public void setIdVacuna(Integer idVacuna) {
        this.idVacuna = idVacuna;
    }

    public Integer getIdSintoma() {
        return idSintoma;
    }

    public void setIdSintoma(Integer idSintoma) {
        this.idSintoma = idSintoma;
    }

    public ReporteAdverso.Sexo getSexo() {
        return sexo;
    }

    public void setSexo(ReporteAdverso.Sexo sexo) {
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
}
