package com.farmacov.application.usecase;

import com.farmacov.application.dto.KpisDashboardDto;
import com.farmacov.domain.repository.ReporteAdversoRepository;
import com.farmacov.domain.repository.VacunaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;

@ApplicationScoped
public class ObtenerKpisDashboardUseCase {

    @Inject
    VacunaRepository vacunaRepository;

    @Inject
    ReporteAdversoRepository reporteAdversoRepository;

    // Los conteos se resuelven en los repositorios; el porcentaje se calcula y redondea aqui.
    public KpisDashboardDto execute() {
        // COUNT(*) — MySQL devuelve solo un número, no objetos
        long totalVacunas = vacunaRepository.countVacunas();
        long totalReportes = reporteAdversoRepository.countAll();
        long totalReportesGraves = reporteAdversoRepository.countByEsGrave(true);

        // MySQL filtra por mes y año — no viajan registros a Java
        long reportesEsteMes = reporteAdversoRepository.countByMesYAnio(
                LocalDate.now().getMonthValue(),
                LocalDate.now().getYear()
        );

        // Porcentaje calculado en Java con los números que ya tenemos
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
