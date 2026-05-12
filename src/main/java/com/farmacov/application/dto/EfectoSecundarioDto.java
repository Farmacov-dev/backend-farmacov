package com.farmacov.application.dto;

import com.farmacov.domain.models.EfectoSecundario;

// DTO que representa un efecto secundario dentro de la respuesta de detalle de vacuna
public class EfectoSecundarioDto {

    // A
    private String descripcion;
    private String severidad; // leve | moderado | grave

    // C
    public EfectoSecundarioDto() {}

    // factory — convierte el modelo de dominio al dto de respuesta
    public static EfectoSecundarioDto fromDomain(EfectoSecundario efecto) {
        EfectoSecundarioDto dto = new EfectoSecundarioDto();
        dto.descripcion = efecto.getDescripcion();
        dto.severidad   = efecto.getSeveridad() != null
                ? efecto.getSeveridad().name()
                : null;
        return dto;
    }

    // G
    public String getDescripcion() {
        return descripcion;
    }

    public String getSeveridad() {
        return severidad;
    }

    // S
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }
}
