package com.farmacov.application.service;

import com.farmacov.application.dto.CrearReporteAdversoDto;
import com.farmacov.domain.models.ReporteAdverso;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class ReporteAdversoService {

    private final ReporteAdversoRepository reporteAdversoRepository;

    @Inject
    public ReporteAdversoService(ReporteAdversoRepository reporteAdversoRepository) {
        this.reporteAdversoRepository = reporteAdversoRepository;
    }

    @Transactional
    public ReporteAdverso crear(CrearReporteAdversoDto dto) {
        ReporteAdverso reporteAdverso = new ReporteAdverso();
        reporteAdverso.setId(dto.getId());
        reporteAdverso.setIdVacuna(dto.getIdVacuna());
        reporteAdverso.setIdSintoma(dto.getIdSintoma());
        reporteAdverso.setSexo(dto.getSexo());
        reporteAdverso.setGrupoEdad(ReporteAdverso.GrupoEdad.fromValue(dto.getGrupoEdad()));
        reporteAdverso.setEsGrave(Boolean.TRUE.equals(dto.getEsGrave()));
        reporteAdverso.setFechaReporte(dto.getFechaReporte());
        return reporteAdversoRepository.save(reporteAdverso);
    }

    public ReporteAdverso getById(Long id) {
        return reporteAdversoRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Reporte adverso con id " + id + " no encontrado"));
    }

    public List<ReporteAdverso> getAll() {
        return reporteAdversoRepository.getAll();
    }
}
