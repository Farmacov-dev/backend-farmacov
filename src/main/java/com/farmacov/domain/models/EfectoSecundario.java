//paso 2 despues de hacer EfectoSecundarioEntity
package com.farmacov.domain.models;

public class EfectoSecundario {

    private Integer id;
    private Integer idVacuna;
    private String descripcion;
    private Severidad severidad;

    // Enum propio del dominio — espeja los valores del ENUM de MySQL
    // y del enum en EfectoSecundarioEntity.
    // Cada capa tiene su propio enum para mantenerse independiente.
    public enum Severidad {
        leve, moderado, grave
    }

    public EfectoSecundario() {}

    // Guardamos solo el ID de la vacuna, no el objeto completo.
    // El dominio de EfectoSecundario no necesita saber todo sobre una vacuna,
    // solo a cuál pertenece. Si se necesitan datos de la vacuna,
    // el UseCase los busca por separado.
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdVacuna() { return idVacuna; }
    public void setIdVacuna(Integer idVacuna) { this.idVacuna = idVacuna; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Severidad getSeveridad() { return severidad; }
    public void setSeveridad(Severidad severidad) { this.severidad = severidad; }
}