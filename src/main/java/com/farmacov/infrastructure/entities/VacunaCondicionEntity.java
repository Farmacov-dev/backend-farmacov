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

    // Relación con la vacuna — LAZY para no cargar datos innecesarios
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_vacuna", nullable = false)
    private VacunaEntity vacuna;

    // DECIMAL(5,1) — 4 enteros máximo, 1 decimal. NOT NULL en el schema.
    @Column(nullable = false, precision = 5, scale = 1)
    private BigDecimal temperatura;

    // DECIMAL(5,1) sin NOT NULL — puede llegar null desde la base de datos.
    // nullable = true es el default pero lo dejamos explícito para que sea
    // claro que es intencional y no un olvido.
    @Column(nullable = true, precision = 5, scale = 1)
    private BigDecimal tiempoAmbiente;

    // updatable = false — refleja que creado_en no tiene ON UPDATE en el schema.
    // Hibernate nunca lo sobreescribe después del INSERT.
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    // actualizado_en sí se actualiza — refleja el ON UPDATE CURRENT_TIMESTAMP
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