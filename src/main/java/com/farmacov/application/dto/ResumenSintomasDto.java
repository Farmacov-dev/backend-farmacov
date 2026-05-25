package com.farmacov.application.dto;

public class ResumenSintomasDto {
    private Integer idVacuna;
    private String  nombreVacuna;
    private Integer idSintoma;
    private String  nombreSintoma;
    private String  sexo;
    private String  grupoEdad;
    private Boolean esGrave;
    private Long    total;

    public ResumenSintomasDto() {}

    public ResumenSintomasDto(Integer idVacuna, String nombreVacuna,
                              Integer idSintoma, String nombreSintoma,
                              String sexo, String grupoEdad,
                              Boolean esGrave, Long total) {
        this.idVacuna     = idVacuna;
        this.nombreVacuna = nombreVacuna;
        this.idSintoma    = idSintoma;
        this.nombreSintoma= nombreSintoma;
        this.sexo         = sexo;
        this.grupoEdad    = grupoEdad;
        this.esGrave      = esGrave;
        this.total        = total;
    }

    public Integer getIdVacuna()      { return idVacuna; }
    public String  getNombreVacuna()  { return nombreVacuna; }
    public Integer getIdSintoma()     { return idSintoma; }
    public String  getNombreSintoma() { return nombreSintoma; }
    public String  getSexo()          { return sexo; }
    public String  getGrupoEdad()     { return grupoEdad; }
    public Boolean getEsGrave()       { return esGrave; }
    public Long    getTotal()         { return total; }
}