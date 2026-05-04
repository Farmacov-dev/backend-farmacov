package com.farmacov.infrastructure.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class RolesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincremetna al ser una tabla pequeña y fija
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "es_admin", nullable = false)
    private boolean esAdmin;

    // C

    public RolesEntity() {}

    // G
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }

    // S
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }
}
