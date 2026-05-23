package com.farmacov.application.dto;

import com.farmacov.domain.models.Vacuna;
import java.math.BigDecimal;

public class VacunaCatalogoResponseDto {

    private Integer    idVacuna;
    private String     nombre;
    private String     farmaceutica;
    private BigDecimal costoUnitario;
    private BigDecimal temperatura;
    private BigDecimal tiempoAmbiente;
    private BigDecimal efectividad;      // calculada en el use case (lógica pendiente de definir)
    private Double     indiceSeguridad;  // calculado vía vista_indice_seguridad / sp_indice_seguridad

    public VacunaCatalogoResponseDto() {}

    // factory — convierte modelo de dominio al dto liviano del catálogo
    // efectividad     → null hasta que se defina la lógica de cálculo
    // indiceSeguridad → porcentaje 0-100 proveniente de la vista / SP (null si no hay reportes aún)
    public static VacunaCatalogoResponseDto fromDomain(Vacuna vacuna,
                                                       BigDecimal efectividad,
                                                       Double indiceSeguridad) {
        VacunaCatalogoResponseDto dto = new VacunaCatalogoResponseDto();
        dto.idVacuna        = vacuna.getIdVacuna();
        dto.nombre          = vacuna.getNombre();
        dto.farmaceutica    = vacuna.getFarmaceutica();
        dto.costoUnitario   = vacuna.getCostoUnitario();
        dto.temperatura     = vacuna.getTemperatura();
        dto.tiempoAmbiente  = vacuna.getTiempoAmbiente();
        dto.efectividad     = efectividad;
        dto.indiceSeguridad = indiceSeguridad;
        return dto;
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

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }

    public BigDecimal getTiempoAmbiente() {
        return tiempoAmbiente;
    }

    public BigDecimal getEfectividad() {
        return efectividad;
    }

    public void setIdVacuna(Integer idVacuna) {
        this.idVacuna = idVacuna;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFarmaceutica(String farmaceutica) {
        this.farmaceutica = farmaceutica;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public void setTemperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
    }

    public void setTiempoAmbiente(BigDecimal tiempoAmbiente) {
        this.tiempoAmbiente = tiempoAmbiente;
    }

    public void setEfectividad(BigDecimal efectividad) {
        this.efectividad = efectividad;
    }

    public Double getIndiceSeguridad() {
        return indiceSeguridad;
    }

    public void setIndiceSeguridad(Double indiceSeguridad) {
        this.indiceSeguridad = indiceSeguridad;
    }
}
