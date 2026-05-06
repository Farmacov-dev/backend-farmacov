package com.farmacov.infrastructure.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacunas_condiciones")
public class VacunaCondicionEntityReferencia {

    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vacuna", nullable = false)
    private VacunaEntity vacuna;

    @Column(name = "temperatura", precision = 5, scale = 1)
    private BigDecimal temperatura;

    @Column(name = "tiempo_ambiente", precision = 5, scale = 1)
    private BigDecimal tiempoAmbiente;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    // C
    public VacunaCondicionEntityReferencia() {}

    // G
    public Integer getId() {
        return id;
    }

    public VacunaEntity getVacuna() {
        return vacuna;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }

    public BigDecimal getTiempoAmbiente() {
        return tiempoAmbiente;
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

    public void setTemperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
    }

    public void setTiempoAmbiente(BigDecimal tiempoAmbiente) {
        this.tiempoAmbiente = tiempoAmbiente;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}
