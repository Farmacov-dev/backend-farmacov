//paso 1 implementacion tabla Efecto Secundario
package com.farmacov.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "efectos_secundarios")
public class EfectoSecundarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vacuna", nullable = false)
    private VacunaEntity vacuna;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Severidad severidad;

    public enum Severidad {
        leve, moderado, grave
    }

    public EfectoSecundarioEntity() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public VacunaEntity getVacuna() { return vacuna; }
    public void setVacuna(VacunaEntity vacuna) { this.vacuna = vacuna; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Severidad getSeveridad() { return severidad; }
    public void setSeveridad(Severidad severidad) { this.severidad = severidad; }
}