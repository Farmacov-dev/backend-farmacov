package com.farmacov.application.dto;

import com.farmacov.domain.models.Vacuna;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// DTO para GET /vacunas/{id} — vista de detalle de una vacuna
// Clase independiente: los campos son distintos a los del catálogo
public class VacunaDetalleResponseDto {

    // A — vacunas
    private String nombre;
    private String farmaceutica;
    private String tipo;

    // A — vacuna_condiciones
    private BigDecimal temperatura;
    private BigDecimal tiempoAmbiente;

    // A — efectos_secundarios (lista completa de la tabla)
    private List<EfectoSecundarioDto> efectosSecundarios;

    // C
    public VacunaDetalleResponseDto() {}

    // factory — construye el dto a partir del modelo de dominio
    public static VacunaDetalleResponseDto fromDomain(Vacuna vacuna) {
        VacunaDetalleResponseDto dto = new VacunaDetalleResponseDto();

        dto.nombre        = vacuna.getNombre();
        dto.farmaceutica  = vacuna.getFarmaceutica();
        dto.tipo          = vacuna.getTipo();
        dto.temperatura   = vacuna.getTemperatura();
        dto.tiempoAmbiente = vacuna.getTiempoAmbiente();

        // convierte cada EfectoSecundario del dominio a su DTO de respuesta
        dto.efectosSecundarios = vacuna.getEfectosSecundarios() != null
                ? vacuna.getEfectosSecundarios().stream()
                        .map(EfectoSecundarioDto::fromDomain)
                        .collect(Collectors.toList())
                : Collections.emptyList();

        return dto;
    }

    // G
    public String getNombre() {
        return nombre;
    }

    public String getFarmaceutica() {
        return farmaceutica;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }

    public BigDecimal getTiempoAmbiente() {
        return tiempoAmbiente;
    }

    public List<EfectoSecundarioDto> getEfectosSecundarios() {
        return efectosSecundarios;
    }

    // S
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFarmaceutica(String farmaceutica) {
        this.farmaceutica = farmaceutica;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTemperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
    }

    public void setTiempoAmbiente(BigDecimal tiempoAmbiente) {
        this.tiempoAmbiente = tiempoAmbiente;
    }

    public void setEfectosSecundarios(List<EfectoSecundarioDto> efectosSecundarios) {
        this.efectosSecundarios = efectosSecundarios;
    }
}
