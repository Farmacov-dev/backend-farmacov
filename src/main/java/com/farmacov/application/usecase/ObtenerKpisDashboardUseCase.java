package com.farmacov.application.usecase;

import com.farmacov.application.dto.KpisDashboardDto;
import com.farmacov.domain.models.ReporteAdverso;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import com.farmacov.domain.repository.VacunaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ObtenerKpisDashboardUseCase {

    @Inject
    VacunaRepository vacunaRepository;

    @Inject
    ReporteAdversoRepository reporteAdversoRepository;

    public KpisDashboardDto execute() {
        // implementado ccunt desde VacunaRepository por optimizacion
        long totalVacunas = vacunaRepository.countVacunas();

        // se traen todos los reportes de una vez
        // se reutilizan en todos los calculos
        List<ReporteAdverso> todosLosReportes = reporteAdversoRepository.getAll();
        long totalReportes = todosLosReportes.size();

        // se filtra por mes y ano actual en Java, esto se tiene que optimizar
        int mesActual = LocalDate.now().getMonthValue();
        int anioActual = LocalDate.now().getYear();
        long reportesEsteMes = todosLosReportes.stream()
                .filter(r -> r.getFechaReporte() != null
                        && r.getFechaReporte().getMonthValue() == mesActual
                        && r.getFechaReporte().getYear() == anioActual)
                .count();

        // reportes graves, usamos el metodo ya existente en el repo
        long totalReportesGraves = reporteAdversoRepository.getByEsGrave(true).size();


        double porcentaje = totalReportes > 0
                ? Math.round((totalReportesGraves * 100.0 / totalReportes) * 10.0) / 10.0
                : 0.0;

        KpisDashboardDto dto = new KpisDashboardDto();
        dto.setTotalVacunas(totalVacunas);
        dto.setTotalReportes(totalReportes);
        dto.setReportesEsteMes(reportesEsteMes);
        dto.setTotalReportesGraves(totalReportesGraves);
        dto.setPorcentajeReportesGraves(porcentaje);

        return dto;
    }
}