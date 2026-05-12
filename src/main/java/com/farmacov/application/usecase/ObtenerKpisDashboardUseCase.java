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
        // COUNT(*) directo — eficiente, no carga objetos en memoria
        long totalVacunas = vacunaRepository.countVacunas();

        // Traemos todos los reportes una sola vez
        // y los reutilizamos para los tres cálculos siguientes
        List<ReporteAdverso> todosLosReportes = reporteAdversoRepository.getAll();
        long totalReportes = todosLosReportes.size();

        // Filtramos por mes y año actual en Java — sin query extra a la BD
        int mesActual = LocalDate.now().getMonthValue();
        int anioActual = LocalDate.now().getYear();
        long reportesEsteMes = todosLosReportes.stream()
                .filter(r -> r.getFechaReporte() != null
                        && r.getFechaReporte().getMonthValue() == mesActual
                        && r.getFechaReporte().getYear() == anioActual)
                .count();

        // Reportes graves — usamos el método ya existente en el repo
        long totalReportesGraves = reporteAdversoRepository.getByEsGrave(true).size();

        // Porcentaje redondeado a 1 decimal
        // Verificamos que total > 0 para evitar división por cero
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