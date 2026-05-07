package com.farmacov.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReporteAdversoResponseDto {
    private Long id;
    private Integer idVacuna;
    private Integer idSintoma;
    private String sexo;
    private String grupoEdad;
    private Boolean esGrave;
    private LocalDate fechaReporte;
    private LocalDateTime creadoEn;

    public ReporteAdversoResponseDto() {
    }

    public ReporteAdversoResponseDto(
            Long id,
            Integer idVacuna,
            Integer idSintoma,
            String sexo,
            String grupoEdad,
            Boolean esGrave,
            LocalDate fechaReporte,
            LocalDateTime creadoEn
    ) {
        this.id = id;
        this.idVacuna = idVacuna;
        this.idSintoma = idSintoma;
        this.sexo = sexo;
        this.grupoEdad = grupoEdad;
        this.esGrave = esGrave;
        this.fechaReporte = fechaReporte;
        this.creadoEn = creadoEn;
    }

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
