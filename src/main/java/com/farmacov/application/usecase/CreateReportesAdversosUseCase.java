package com.farmacov.application.usecase;


import com.farmacov.application.dto.CrearReporteAdversoDto;
import com.farmacov.application.dto.ReporteAdversoResponseDto;
import com.farmacov.domain.models.ReporteAdverso;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CreateReportesAdversosUseCase {

    private final ReporteAdversoRepository reporteAdversoRepository;

    @Inject
    public CreateReportesAdversosUseCase(ReporteAdversoRepository reporteAdversoRepository){
        this.reporteAdversoRepository = reporteAdversoRepository;
    }

    public ReporteAdverso execute(CrearReporteAdversoDto dto){
        ReporteAdverso reporteAdverso = new ReporteAdverso();

        reporteAdverso.setId(dto.getId());
        reporteAdverso.setIdVacuna(dto.getIdVacuna());
        reporteAdverso.setSexo(dto.getSexo());
        reporteAdverso.setGrupoEdad(ReporteAdverso.GrupoEdad.fromValue((dto.getGrupoEdad())));
        reporteAdverso.setEsGrave(dto.getEsGrave());
        reporteAdverso.setFechaReporte(dto.getFechaReporte());

        return reporteAdversoRepository.save(reporteAdverso);
    }
}
