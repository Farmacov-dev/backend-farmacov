package com.farmacov.infrastructure.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "sintomas_graves")
public class SintomaGraveEntityReferencia {

    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vacuna", nullable = false)
    private VacunaEntity vacuna;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    // C
    public SintomaGraveEntityReferencia() {}

    // G
    public Integer getId() {
        return id;
    }

    public VacunaEntity getVacuna() {
        return vacuna;
    }

    public String getNombre() {
        return nombre;
    }

    // S
    public void setId(Integer id) {
        this.id = id;
    }

    public void setVacuna(VacunaEntity vacuna) {
        this.vacuna = vacuna;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
