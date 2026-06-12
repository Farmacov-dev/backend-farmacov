package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.EfectoSecundario;
import com.farmacov.domain.models.Vacuna;
import com.farmacov.infrastructure.entities.EfectoSecundarioEntity;
import com.farmacov.infrastructure.entities.FarmacoEntity;
import com.farmacov.infrastructure.entities.VacunaCondicionEntity;
import com.farmacov.infrastructure.entities.VacunaCostoEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VacunaMapperTest {

    // ─── toDomain ───────────────────────────────────────────

    @Test
    void toDomain_debeMapearCamposPropiosDeVacuna() {
        // Arrange
        VacunaEntity entity = buildEntityBase();

        // Act
        Vacuna modelo = VacunaMapper.toDomain(entity);

        // Assert
        assertEquals(1, modelo.getIdVacuna());
        assertEquals("Pfizer", modelo.getNombre());
        assertEquals("Pfizer Inc.", modelo.getFarmaceutica());
        assertEquals("ARNm", modelo.getTipo());
        assertEquals("Vacuna contra COVID-19", modelo.getDescripcionGeneral());
        assertNotNull(modelo.getCreadoEn());
        assertNotNull(modelo.getActualizadoEn());
    }

    @Test
    void toDomain_conFarmaco_debeMapearIdYNombreFarmaco() {
        // Arrange
        FarmacoEntity farmaco = new FarmacoEntity();
        farmaco.setId(10);
        farmaco.setNombre("tozinameran");

        VacunaEntity entity = buildEntityBase();
        entity.setFarmaco(farmaco);

        // Act
        Vacuna modelo = VacunaMapper.toDomain(entity);

        // Assert
        assertEquals(10, modelo.getIdFarmaco());
        assertEquals("tozinameran", modelo.getNombreFarmaco());
    }

    @Test
    void toDomain_sinFarmaco_debeDejarIdFarmacoNull() {
        VacunaEntity entity = buildEntityBase();
        entity.setFarmaco(null);

        Vacuna modelo = VacunaMapper.toDomain(entity);

        assertNull(modelo.getIdFarmaco());
        assertNull(modelo.getNombreFarmaco());
    }

    @Test
    void toDomain_conUnaCondicion_debeMapearTemperaturaYTiempoAmbiente() {
        // Arrange
        VacunaCondicionEntity condicion = new VacunaCondicionEntity();
        condicion.setId(1);
        condicion.setTemperatura(new BigDecimal("2.0"));
        condicion.setTiempoAmbiente(new BigDecimal("4.0"));

        VacunaEntity entity = buildEntityBase();
        entity.setCondiciones(List.of(condicion));

        // Act
        Vacuna modelo = VacunaMapper.toDomain(entity);

        // Assert
        assertEquals(new BigDecimal("2.0"), modelo.getTemperatura());
        assertEquals(new BigDecimal("4.0"), modelo.getTiempoAmbiente());
    }

    @Test
    void toDomain_conUnCosto_debeMapearCostoUnitario() {
        // Arrange
        VacunaCostoEntity costo = new VacunaCostoEntity();
        costo.setId(1);
        costo.setCostoUnitario(new BigDecimal("250.00"));

        VacunaEntity entity = buildEntityBase();
        entity.setCostos(List.of(costo));

        // Act
        Vacuna modelo = VacunaMapper.toDomain(entity);

        // Assert
        assertEquals(new BigDecimal("250.00"), modelo.getCostoUnitario());
    }

    @Test
    void toDomain_conVariasCondiciones_debeTomarSoloPrimera() {
        // El mapper usa get(0) — solo toma la primera condición
        VacunaCondicionEntity condicion1 = new VacunaCondicionEntity();
        condicion1.setId(1);
        condicion1.setTemperatura(new BigDecimal("-70.0"));

        VacunaCondicionEntity condicion2 = new VacunaCondicionEntity();
        condicion2.setId(2);
        condicion2.setTemperatura(new BigDecimal("2.0"));

        VacunaEntity entity = buildEntityBase();
        entity.setCondiciones(List.of(condicion1, condicion2));

        Vacuna modelo = VacunaMapper.toDomain(entity);

        assertEquals(new BigDecimal("-70.0"), modelo.getTemperatura());
    }

    @Test
    void toDomain_conMultiplesEfectos_debeMapearListaCompleta() {
        // A diferencia de condiciones y costos, efectos se mapean todos
        EfectoSecundarioEntity efecto1 = buildEfecto(1, "Dolor en brazo",
                EfectoSecundarioEntity.Severidad.leve);
        EfectoSecundarioEntity efecto2 = buildEfecto(2, "Fiebre",
                EfectoSecundarioEntity.Severidad.moderado);
        EfectoSecundarioEntity efecto3 = buildEfecto(3, "Anafilaxia",
                EfectoSecundarioEntity.Severidad.grave);

        VacunaEntity entity = buildEntityBase();
        entity.setEfectosSecundarios(List.of(efecto1, efecto2, efecto3));

        Vacuna modelo = VacunaMapper.toDomain(entity);

        assertEquals(3, modelo.getEfectosSecundarios().size());
        assertEquals("Dolor en brazo", modelo.getEfectosSecundarios().get(0).getDescripcion());
        assertEquals(EfectoSecundario.Severidad.leve,
                modelo.getEfectosSecundarios().get(0).getSeveridad());
        assertEquals("Anafilaxia", modelo.getEfectosSecundarios().get(2).getDescripcion());
        assertEquals(EfectoSecundario.Severidad.grave,
                modelo.getEfectosSecundarios().get(2).getSeveridad());
    }

    @Test
    void toDomain_conEfectosSeveridadNull_debeMapearSeveridadNull() {
        EfectoSecundarioEntity efecto = new EfectoSecundarioEntity();
        efecto.setId(1);
        efecto.setDescripcion("Efecto desconocido");
        efecto.setSeveridad(null);

        VacunaEntity entity = buildEntityBase();
        entity.setEfectosSecundarios(List.of(efecto));

        Vacuna modelo = VacunaMapper.toDomain(entity);

        assertNull(modelo.getEfectosSecundarios().get(0).getSeveridad());
    }

    @Test
    void toDomain_conListasVacias_debeDejarCamposSubtablaNull() {
        VacunaEntity entity = buildEntityBase();
        entity.setCondiciones(List.of());
        entity.setCostos(List.of());
        entity.setEfectosSecundarios(List.of());

        Vacuna modelo = VacunaMapper.toDomain(entity);

        assertNull(modelo.getTemperatura());
        assertNull(modelo.getTiempoAmbiente());
        assertNull(modelo.getCostoUnitario());
        assertTrue(modelo.getEfectosSecundarios().isEmpty());
    }

    @Test
    void toDomain_conEntityNull_debeRetornarNull() {
        assertNull(VacunaMapper.toDomain(null));
    }

    // ─── toEntity ───────────────────────────────────────────

    @Test
    void toEntity_debeMapearSoloCamposPropiosDeVacuna() {
        // Arrange
        Vacuna modelo = new Vacuna();
        modelo.setIdVacuna(1);
        modelo.setNombre("Pfizer");
        modelo.setFarmaceutica("Pfizer Inc.");
        modelo.setTipo("ARNm");
        modelo.setDescripcionGeneral("Vacuna contra COVID-19");
        LocalDateTime ahora = LocalDateTime.now();
        modelo.setCreadoEn(ahora);
        modelo.setActualizadoEn(ahora);

        // Act
        VacunaEntity entity = VacunaMapper.toEntity(modelo);

        // Assert
        assertEquals(1, entity.getId());
        assertEquals("Pfizer", entity.getNombre());
        assertEquals("Pfizer Inc.", entity.getFarmaceutica());
        assertEquals("ARNm", entity.getTipo());
        assertEquals("Vacuna contra COVID-19", entity.getDescripcionGeneral());
        assertEquals(ahora, entity.getCreadoEn());
        assertEquals(ahora, entity.getActualizadoEn());
        // subtablas y farmaco no se tocan en toEntity
        assertNull(entity.getCondiciones());
        assertNull(entity.getCostos());
        assertNull(entity.getEfectosSecundarios());
        assertNull(entity.getFarmaco());
    }

    @Test
    void toEntity_conModeloNull_debeRetornarNull() {
        assertNull(VacunaMapper.toEntity(null));
    }

    // ─── helpers ────────────────────────────────────────────

    private VacunaEntity buildEntityBase() {
        VacunaEntity entity = new VacunaEntity();
        entity.setId(1);
        entity.setNombre("Pfizer");
        entity.setFarmaceutica("Pfizer Inc.");
        entity.setTipo("ARNm");
        entity.setDescripcionGeneral("Vacuna contra COVID-19");
        entity.setCreadoEn(LocalDateTime.now());
        entity.setActualizadoEn(LocalDateTime.now());
        entity.setCondiciones(List.of());
        entity.setCostos(List.of());
        entity.setEfectosSecundarios(List.of());
        entity.setSintomasGraves(List.of());
        return entity;
    }

    private EfectoSecundarioEntity buildEfecto(Integer id, String descripcion,
                                               EfectoSecundarioEntity.Severidad severidad) {
        EfectoSecundarioEntity efecto = new EfectoSecundarioEntity();
        efecto.setId(id);
        efecto.setDescripcion(descripcion);
        efecto.setSeveridad(severidad);
        return efecto;
    }
}