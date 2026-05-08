package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.Vacuna;
import com.farmacov.infrastructure.entities.EfectoSecundarioEntity;
import com.farmacov.infrastructure.entities.SintomaGraveEntity;
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
        VacunaEntity entity = new VacunaEntity();
        entity.setId(1);
        entity.setNombre("Pfizer");
        entity.setFarmaceutica("Pfizer Inc.");
        entity.setTipo("ARNm");
        entity.setDescripcionGeneral("Vacuna contra COVID-19");
        LocalDateTime ahora = LocalDateTime.now();
        entity.setCreadoEn(ahora);
        entity.setActualizadoEn(ahora);
        entity.setCondiciones(List.of());
        entity.setCostos(List.of());
        entity.setEfectosSecundarios(List.of());
        entity.setSintomasGraves(List.of());

        // Act
        Vacuna modelo = VacunaMapper.toDomain(entity);

        // Assert
        assertEquals(1, modelo.getIdVacuna());
        assertEquals("Pfizer", modelo.getNombre());
        assertEquals("Pfizer Inc.", modelo.getFarmaceutica());
        assertEquals("ARNm", modelo.getTipo());
        assertEquals("Vacuna contra COVID-19", modelo.getDescripcionGeneral());
        assertEquals(ahora, modelo.getCreadoEn());
        assertEquals(ahora, modelo.getActualizadoEn());
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
    void toDomain_conUnEfecto_debeMapearSoloElPrimero() {
        // Arrange — dos efectos, solo el primero se mapea hasta que se arregle vacuna
        EfectoSecundarioEntity efecto1 = new EfectoSecundarioEntity();
        efecto1.setId(1);
        efecto1.setDescripcion("Dolor en el brazo");
        efecto1.setSeveridad(EfectoSecundarioEntity.Severidad.leve);

        EfectoSecundarioEntity efecto2 = new EfectoSecundarioEntity();
        efecto2.setId(2);
        efecto2.setDescripcion("Anafilaxia");
        efecto2.setSeveridad(EfectoSecundarioEntity.Severidad.grave);

        VacunaEntity entity = buildEntityBase();
        entity.setEfectosSecundarios(List.of(efecto1, efecto2));

        // Act
        Vacuna modelo = VacunaMapper.toDomain(entity);

        // Assert — solo mapea el primero el segundo se pierde
        assertEquals("Dolor en el brazo", modelo.getDescripcionEfecto());
        assertEquals("leve", modelo.getSeveridadEfecto());
        // ⚠"Anafilaxia" y "grave" se pierden, este test documenta la limitacion
    }

    @Test
    void toDomain_conUnSintoma_debeMapearSoloElPrimero() {
        // Arrange - dos sintomas pero solo el primero se va a mappear
        SintomaGraveEntity sintoma1 = new SintomaGraveEntity();
        sintoma1.setId(1);
        sintoma1.setNombre("Fiebre alta");

        SintomaGraveEntity sintoma2 = new SintomaGraveEntity();
        sintoma2.setId(2);
        sintoma2.setNombre("Convulsiones");

        VacunaEntity entity = buildEntityBase();
        entity.setSintomasGraves(List.of(sintoma1, sintoma2));

        // Act
        Vacuna modelo = VacunaMapper.toDomain(entity);

        // Assert —  prueba de si solo se mappea el primer valor
        assertEquals("Fiebre alta", modelo.getNombreSintomaGrave());
        // si pasa, convulsiones se pierde ,  esto documenta la limitacion
    }

    @Test
    void toDomain_conListasVacias_debeDejarCamposSubtablaNull() {
        // Arrange
        VacunaEntity entity = buildEntityBase();
        entity.setCondiciones(List.of());
        entity.setCostos(List.of());
        entity.setEfectosSecundarios(List.of());
        entity.setSintomasGraves(List.of());

        // Act
        Vacuna modelo = VacunaMapper.toDomain(entity);

        // Assert
        assertNull(modelo.getTemperatura());
        assertNull(modelo.getTiempoAmbiente());
        assertNull(modelo.getCostoUnitario());
        assertNull(modelo.getDescripcionEfecto());
        assertNull(modelo.getSeveridadEfecto());
        assertNull(modelo.getNombreSintomaGrave());
    }

    @Test
    void toDomain_conEntityNull_debeRetornarNull() {
        assertNull(VacunaMapper.toDomain(null));
    }

    // toENTITY

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
        // subtablas no se tocan en toEntity
        assertNull(entity.getCondiciones());
        assertNull(entity.getCostos());
        assertNull(entity.getEfectosSecundarios());
        assertNull(entity.getSintomasGraves());
    }

    @Test
    void toEntity_conModeloNull_debeRetornarNull() {
        assertNull(VacunaMapper.toEntity(null));
    }

    // helper

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
}