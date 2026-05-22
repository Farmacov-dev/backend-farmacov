package com.farmacov.application.dto;
import java.math.BigDecimal;

public class CostosPorVacunaDto {

    private String nombreVacuna;
    private BigDecimal costoUnitario;

    public CostosPorVacunaDto(){

    }

    // G
    public String getNombreVacuna(){
        return nombreVacuna;
    }

    public BigDecimal getCostoUnitario(){
        return costoUnitario;
    }

    // S
    public void setNombreVacuna(String nombreVacuna){
        this.nombreVacuna = nombreVacuna;
    }

    public void setCostoUnitario(BigDecimal costoUnitario){
        this.costoUnitario = costoUnitario;
    }
}
