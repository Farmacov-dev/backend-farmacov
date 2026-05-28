package com.farmacov.infrastructure.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "anotaciones")
public class AnotacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relacion LAZY para no cargar el usuario cuando no hace falta.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuariosEntity usuario;

    @Column(name = "dashboard_referencia", length = 150)
    private String dashboardReferencia;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "creado_en", nullable = false, updatable = false)
    // Se setea al insertar desde el repositorio.
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en", nullable = false)
    // Se actualiza en cada modificacion.
    private LocalDateTime actualizadoEn;

    public AnotacionEntity(){

    }

    public Integer getId(){
        return id;
    }
    public UsuariosEntity getUsuario(){
        return usuario;
    }

    public String getDashboardReferencia(){
        return dashboardReferencia;
    }

    public String getTitulo(){
        return titulo;
    }
    public String getObservaciones() {
        return observaciones;
    }

    public LocalDateTime getCreadoEn(){
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn(){
        return actualizadoEn;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setUsuario(UsuariosEntity usuario){
        this.usuario = usuario;
    }

    public void setDashboardReferencia(String dashboardReferencia){
        this.dashboardReferencia = dashboardReferencia;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setObservaciones(String observaciones){
        this.observaciones = observaciones;
    }

    public void setCreadoEn(LocalDateTime creadoEn){
        this.creadoEn = creadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn){
        this.actualizadoEn = actualizadoEn;
    }
}
