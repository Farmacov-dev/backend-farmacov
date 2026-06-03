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

import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora")
public class BitacoraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_admin", nullable = false)
    private UsuariosEntity admin;

    @Column(name = "accion", nullable = false, length = 10)
    private String accion;

    // nullable=true: cuando el usuario afectado es eliminado, la FK se pone a NULL
    // para conservar el registro de auditoría (accion=DELETE) en bitácora
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_usuario_afectado", nullable = true)
    private UsuariosEntity usuarioAfectado;

    @Column(name = "creado_en", nullable = false, insertable = false, updatable = false)
    private LocalDateTime creadoEn;

    // C

    public BitacoraEntity() {
    }

    // G

    public Long getId() {
        return id;
    }

    public UsuariosEntity getAdmin() {
        return admin;
    }

    public String getAccion() {
        return accion;
    }

    public UsuariosEntity getUsuarioAfectado() {
        return usuarioAfectado;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    // S

    public void setId(Long id) {
        this.id = id;
    }

    public void setAdmin(UsuariosEntity admin) {
        this.admin = admin;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public void setUsuarioAfectado(UsuariosEntity usuarioAfectado) {
        this.usuarioAfectado = usuarioAfectado;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
}
