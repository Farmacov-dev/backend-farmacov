package com.farmacov.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sintomas_graves")
public class SintomaGraveEntity {

    // INT AUTO_INCREMENT — Hibernate delega la generación del ID a MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // relacion bidireccional con VacunaEntity.
    // "vacuna" es el nombre de este campo, debe coincidir con el mappedBy = "vacuna" declarado en VacunaEntity.sintomasGraves
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vacuna", nullable = false)
    private VacunaEntity vacuna;

    // cambio por que es VARCHAR(150) NOT NULL en el schema
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    public SintomaGraveEntity() {}
    //cambio a getId
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public VacunaEntity getVacuna() { return vacuna; }
    public void setVacuna(VacunaEntity vacuna) { this.vacuna = vacuna; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}