package com.farmacov.application.dto;

import com.farmacov.domain.models.Vacuna;
import java.math.BigDecimal;

public class VacunaDetalleResponseDto extends VacunaCatalogoResponseDto {

    private String tipo;
    private String descripcionGeneral;
    private String descripcionEfecto;
    private String severidadEfecto;
    private String nombreSintomaGrave;

    public VacunaDetalleResponseDto() {}

    public static VacunaDetalleResponseDto fromDomain(Vacuna vacuna, BigDecimal efectividad) {
        VacunaDetalleResponseDto dto = new VacunaDetalleResponseDto();

        // campos heredados del catálogo
        dto.setIdVacuna(vacuna.getIdVacuna());
        dto.setNombre(vacuna.getNombre());
        dto.setFarmaceutica(vacuna.getFarmaceutica());
        dto.setCostoUnitario(vacuna.getCostoUnitario());
        dto.setTemperatura(vacuna.getTemperatura());
        dto.setTiempoAmbiente(vacuna.getTiempoAmbiente());
        dto.setEfectividad(efectividad);

        // campos propios del detalle
        dto.tipo              = vacuna.getTipo();
        dto.descripcionGeneral = vacuna.getDescripcionGeneral();
        dto.descripcionEfecto = vacuna.getDescripcionEfecto();
        dto.severidadEfecto   = vacuna.getSeveridadEfecto();
        dto.nombreSintomaGrave = vacuna.getNombreSintomaGrave();

        return dto;
    }


    public String getTipo() {
        return tipo;
    }

    public String getDescripcionGeneral() {
        return descripcionGeneral;
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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcionGeneral(String descripcionGeneral) {
        this.descripcionGeneral = descripcionGeneral;
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
