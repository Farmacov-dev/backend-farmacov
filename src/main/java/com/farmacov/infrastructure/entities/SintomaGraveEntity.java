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
    // MySQL genera el id; Hibernate solo lo recupera despues del persist.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relacion LAZY: se carga la vacuna solo cuando hace falta, no en cada consulta.
    // El nombre del campo debe coincidir con el mappedBy de VacunaEntity.
    // relacion bidireccional con VacunaEntity.
    // "vacuna" es el nombre de este campo, debe coincidir con el mappedBy = "vacuna" declarado en VacunaEntity.sintomasGraves
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vacuna", nullable = false)
    private VacunaEntity vacuna;

    // Limite impuesto por el schema para evitar textos mas largos que la columna.
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
