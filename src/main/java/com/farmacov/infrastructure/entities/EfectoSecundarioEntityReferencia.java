package com.farmacov.infrastructure.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "efectos_secundarios")
public class EfectoSecundarioEntityReferencia {

    public enum Severidad {
        LEVE,
        MODERADO,
        GRAVE
    }

    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vacuna", nullable = false)
    private VacunaEntity vacuna;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "severidad")
    private Severidad severidad;

    // C
    public EfectoSecundarioEntityReferencia() {}

    // G
    public Integer getId() {
        return id;
    }

    public VacunaEntity getVacuna() {
        return vacuna;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Severidad getSeveridad() {
        return severidad;
    }

    // S
    public void setId(Integer id) {
        this.id = id;
    }

    public void setVacuna(VacunaEntity vacuna) {
        this.vacuna = vacuna;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setSeveridad(Severidad severidad) {
        this.severidad = severidad;
    }
}
