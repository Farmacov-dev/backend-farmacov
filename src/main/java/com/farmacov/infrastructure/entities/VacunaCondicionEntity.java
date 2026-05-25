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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacuna_condiciones")
public class VacunaCondicionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vacuna", nullable = false)
    private VacunaEntity vacuna;

    @Column(nullable = false, precision = 5, scale = 1)
    private BigDecimal temperatura;


    @Column(name = "tiempo_ambiente", nullable = true, precision = 5, scale = 1)
    private BigDecimal tiempoAmbiente;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime actualizadoEn;

    public VacunaCondicionEntity() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public VacunaEntity getVacuna() { return vacuna; }
    public void setVacuna(VacunaEntity vacuna) { this.vacuna = vacuna; }

    public BigDecimal getTemperatura() { return temperatura; }
    public void setTemperatura(BigDecimal temperatura) { this.temperatura = temperatura; }

    public BigDecimal getTiempoAmbiente() { return tiempoAmbiente; }
    public void setTiempoAmbiente(BigDecimal tiempoAmbiente) { this.tiempoAmbiente = tiempoAmbiente; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }

    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(LocalDateTime actualizadoEn) { this.actualizadoEn = actualizadoEn; }
}