package com.farmacov.infrastructure.entities;
import com.farmacov.infrastructure.config.PermisosConverter;
import jakarta.persistence.*;

import java.util.Map;

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

    // adicion para toggles de permisos
    @Convert(converter = PermisosConverter.class)
    @Column(name = "permisos", columnDefinition = "JSON")
    private Map<String, Boolean> permisos;

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

    // adicion para toggles de permisos
    public Map<String, Boolean> getPermisos() {return permisos;}

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

    // adicion para toggles de permisos
    public void setPermisos(Map<String, Boolean> permisos) {this.permisos = permisos;}
}
