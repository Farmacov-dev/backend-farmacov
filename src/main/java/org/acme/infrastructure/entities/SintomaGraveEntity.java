package org.acme.infrastructure.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sintomas_graves")
public class SintomaGraveEntity {

    @Id
    private Integer id;

    @Column(name = "id_vacuna", nullable = false)
    private Integer idVacuna;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    public SintomaGraveEntity(){

    }
    public Integer getid(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getIdVacuna() {
        return idVacuna;
    }

    public void setIdVacuna(Integer idVacuna){
        this.idVacuna = idVacuna;
    }

    public String getNombre() {
            return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }


}