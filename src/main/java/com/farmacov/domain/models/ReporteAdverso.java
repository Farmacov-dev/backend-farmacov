package com.farmacov.domain.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReporteAdverso {
    private Long id;
    private Integer idVacuna;
    private Integer idSintoma;
    private Sexo sexo;
    private GrupoEdad grupoEdad;
    private Boolean esGrave;
    private LocalDate fechaReporte;
    private LocalDateTime creadoEn;

    public enum Sexo {
        M, F, U
    }

    public enum GrupoEdad {
        _0_17("0-17"),
        _18_29("18-29"),
        _30_49("30-49"),
        _50_64("50-64"),
        _65_MAS("65+"),
        DESCONOCIDO("DESCONOCIDO");

        private final String value;

        GrupoEdad(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static GrupoEdad fromValue(String value) {
            for (GrupoEdad grupoEdad : values()) {
                if (grupoEdad.value.equals(value)) {
                    return grupoEdad;
                }
            }
            throw new IllegalArgumentException("Grupo de edad no valido: " + value);
        }
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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public GrupoEdad getGrupoEdad() {
        return grupoEdad;
    }

    public void setGrupoEdad(GrupoEdad grupoEdad) {
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
