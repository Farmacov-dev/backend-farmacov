package com.farmacov.infrastructure.mapper;

import com.farmacov.domain.models.ReporteAdverso;
import com.farmacov.infrastructure.entities.ReporteAdversoEntity;
import com.farmacov.infrastructure.entities.SintomaGraveEntity;
import com.farmacov.infrastructure.entities.VacunaEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReporteAdversoMapperTest {

    // ─── toDomain ───────────────────────────────────────────

    @Test
    void toDomain_debeMapearTodosLosCampos() {
        // Arrange
        VacunaEntity vacuna = new VacunaEntity();
        vacuna.setId(1);

        SintomaGraveEntity sintoma = new SintomaGraveEntity();
        sintoma.setId(2);

        ReporteAdversoEntity entity = new ReporteAdversoEntity();
        entity.setId(100L);
        entity.setVacuna(vacuna);
        entity.setSintomaGrave(sintoma);
        entity.setSexo("F");
        entity.setGrupoEdad("18-29");
        entity.setEsGrave(true);
        entity.setFechaReporte(LocalDate.of(2026, 1, 15));
        entity.setCreadoEn(LocalDateTime.of(2026, 1, 15, 10, 0));

        // Act
        ReporteAdverso modelo = ReporteAdversoMapper.toDomain(entity);

        // Assert
        assertEquals(100L, modelo.getId());
        assertEquals(1, modelo.getIdVacuna());
        assertEquals(2, modelo.getIdSintoma());
        assertEquals(ReporteAdverso.Sexo.F, modelo.getSexo());
        assertEquals(ReporteAdverso.GrupoEdad._18_29, modelo.getGrupoEdad());
        assertTrue(modelo.getEsGrave());
        assertEquals(LocalDate.of(2026, 1, 15), modelo.getFechaReporte());
        assertEquals(LocalDateTime.of(2026, 1, 15, 10, 0), modelo.getCreadoEn());
    }

    @Test
    void toDomain_conVacunaNull_debeMapearIdVacunaNull() {
        ReporteAdversoEntity entity = new ReporteAdversoEntity();
        entity.setId(1L);
        entity.setVacuna(null);
        entity.setSintomaGrave(null);
        entity.setSexo("M");
        entity.setGrupoEdad("65+");
        entity.setEsGrave(false);
        entity.setFechaReporte(LocalDate.now());

        ReporteAdverso modelo = ReporteAdversoMapper.toDomain(entity);

        assertNull(modelo.getIdVacuna());
        assertNull(modelo.getIdSintoma());
    }

    @Test
    void toDomain_conSintomaNull_debeMapearIdSintomaNull() {
        VacunaEntity vacuna = new VacunaEntity();
        vacuna.setId(1);

        ReporteAdversoEntity entity = new ReporteAdversoEntity();
        entity.setId(1L);
        entity.setVacuna(vacuna);
        entity.setSintomaGrave(null); // ← sintoma es opcional
        entity.setSexo("U");
        entity.setGrupoEdad("DESCONOCIDO");
        entity.setEsGrave(false);
        entity.setFechaReporte(LocalDate.now());

        ReporteAdverso modelo = ReporteAdversoMapper.toDomain(entity);

        assertEquals(1, modelo.getIdVacuna());
        assertNull(modelo.getIdSintoma());
    }

    @Test
    void toDomain_todosLosValoresDeSexo_debenMapearCorrectamente() {
        for (String sexo : new String[]{"M", "F", "U"}) {
            ReporteAdversoEntity entity = new ReporteAdversoEntity();
            entity.setId(1L);
            entity.setSexo(sexo);
            entity.setGrupoEdad("18-29");
            entity.setEsGrave(false);
            entity.setFechaReporte(LocalDate.now());

            ReporteAdverso modelo = ReporteAdversoMapper.toDomain(entity);

            assertEquals(ReporteAdverso.Sexo.valueOf(sexo), modelo.getSexo());
        }
    }

    @Test
    void toDomain_todosLosValoresDeGrupoEdad_debenMapearCorrectamente() {
        String[][] casos = {
                {"0-17",        "ReporteAdverso.GrupoEdad._0_17"},
                {"18-29",       "ReporteAdverso.GrupoEdad._18_29"},
                {"30-49",       "ReporteAdverso.GrupoEdad._30_49"},
                {"50-64",       "ReporteAdverso.GrupoEdad._50_64"},
                {"65+",         "ReporteAdverso.GrupoEdad._65_MAS"},
                {"DESCONOCIDO", "ReporteAdverso.GrupoEdad.DESCONOCIDO"}
        };

        ReporteAdverso.GrupoEdad[] esperados = {
                ReporteAdverso.GrupoEdad._0_17,
                ReporteAdverso.GrupoEdad._18_29,
                ReporteAdverso.GrupoEdad._30_49,
                ReporteAdverso.GrupoEdad._50_64,
                ReporteAdverso.GrupoEdad._65_MAS,
                ReporteAdverso.GrupoEdad.DESCONOCIDO
        };

        for (int i = 0; i < esperados.length; i++) {
            ReporteAdversoEntity entity = new ReporteAdversoEntity();
            entity.setId(1L);
            entity.setSexo("M");
            entity.setGrupoEdad(casos[i][0]);
            entity.setEsGrave(false);
            entity.setFechaReporte(LocalDate.now());

            ReporteAdverso modelo = ReporteAdversoMapper.toDomain(entity);

            assertEquals(esperados[i], modelo.getGrupoEdad(), "Fallo para: " + casos[i][0]);
        }
    }

    @Test
    void toDomain_conGrupoEdadInvalido_debeLanzarIllegalArgumentException() {
        ReporteAdversoEntity entity = new ReporteAdversoEntity();
        entity.setId(1L);
        entity.setSexo("M");
        entity.setGrupoEdad("invalido");
        entity.setEsGrave(false);
        entity.setFechaReporte(LocalDate.now());

        assertThrows(IllegalArgumentException.class,
                () -> ReporteAdversoMapper.toDomain(entity));
    }

    // ─── toEntity ───────────────────────────────────────────

    @Test
    void toEntity_debeMapearCamposBasicos_sinRelaciones() {
        // Arrange
        ReporteAdverso modelo = new ReporteAdverso();
        modelo.setId(100L);
        modelo.setIdVacuna(1);
        modelo.setIdSintoma(2);
        modelo.setSexo(ReporteAdverso.Sexo.F);
        modelo.setGrupoEdad(ReporteAdverso.GrupoEdad._18_29);
        modelo.setEsGrave(true);
        modelo.setFechaReporte(LocalDate.of(2026, 1, 15));
        modelo.setCreadoEn(LocalDateTime.of(2026, 1, 15, 10, 0));

        // Act
        ReporteAdversoEntity entity = ReporteAdversoMapper.toEntity(modelo);

        // Assert
        assertEquals(100L, entity.getId());
        assertEquals("F", entity.getSexo());
        assertEquals("18-29", entity.getGrupoEdad());
        assertTrue(entity.getEsGrave());
        assertEquals(LocalDate.of(2026, 1, 15), entity.getFechaReporte());
        assertEquals(LocalDateTime.of(2026, 1, 15, 10, 0), entity.getCreadoEn());
        // vacuna y sintoma no se setean en el mapper — responsabilidad del RepositoryImpl
        assertNull(entity.getVacuna());
        assertNull(entity.getSintomaGrave());
    }

    @Test
    void toEntity_grupoEdad65Mas_debeSerString65Mas() {
        ReporteAdverso modelo = new ReporteAdverso();
        modelo.setId(1L);
        modelo.setSexo(ReporteAdverso.Sexo.M);
        modelo.setGrupoEdad(ReporteAdverso.GrupoEdad._65_MAS);
        modelo.setEsGrave(false);
        modelo.setFechaReporte(LocalDate.now());

        ReporteAdversoEntity entity = ReporteAdversoMapper.toEntity(modelo);

        // _65_MAS.getValue() debe retornar "65+" para MySQL
        assertEquals("65+", entity.getGrupoEdad());
    }

    @Test
    void toEntity_grupoEdadDesconocido_debeSerStringDesconocido() {
        ReporteAdverso modelo = new ReporteAdverso();
        modelo.setId(1L);
        modelo.setSexo(ReporteAdverso.Sexo.U);
        modelo.setGrupoEdad(ReporteAdverso.GrupoEdad.DESCONOCIDO);
        modelo.setEsGrave(false);
        modelo.setFechaReporte(LocalDate.now());

        ReporteAdversoEntity entity = ReporteAdversoMapper.toEntity(modelo);

        assertEquals("DESCONOCIDO", entity.getGrupoEdad());
    }
}