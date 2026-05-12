package com.farmacov.application.dto;

//import com.farmacov.infrastructure.entities.EfectoSecundarioEntity.Severidad;
import com.farmacov.domain.models.EfectoSecundario.Severidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ActualizarEfectoSecundarioDto {

    // Al actualizar no se cambia la vacuna asociada —
    // solo se corrige descripción y/o severidad
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "La descripción no puede superar 200 caracteres")
    private String descripcion;

    @NotNull(message = "La severidad es obligatoria")
    private Severidad severidad;

    public ActualizarEfectoSecundarioDto() {}

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Severidad getSeveridad() { return severidad; }
    public void setSeveridad(Severidad severidad) { this.severidad = severidad; }
}