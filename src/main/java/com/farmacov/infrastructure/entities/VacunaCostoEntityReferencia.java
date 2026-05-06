package com.farmacov.infrastructure.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacuna_costos")
public class VacunaCostoEntityReferencia {

    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vacuna", nullable = false)
    private VacunaEntity vacuna;

    @Column(name = "costo_unitario", precision = 8, scale = 2)
    private BigDecimal costoUnitario;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    // C
    public VacunaCostoEntityReferencia() {}

    // G
    public Integer getId() {
        return id;
    }

    public VacunaEntity getVacuna() {
        return vacuna;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    // S
    public void setId(Integer id) {
        this.id = id;
    }

    public void setVacuna(VacunaEntity vacuna) {
        this.vacuna = vacuna;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}
