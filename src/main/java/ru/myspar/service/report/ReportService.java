package ru.myspar.service.report;

import ru.myspar.dto.report.ComplianceReportDto;
import ru.myspar.dto.report.DailyReportDto;
import ru.myspar.dto.report.MealHistoryDto;

import java.time.LocalDate;

public interface ReportService {
    DailyReportDto generateDailyReport(Integer userId);

    ComplianceReportDto checkCompliance(Integer userId);

    MealHistoryDto getMealHistory(Integer userId, LocalDate startDate, LocalDate endDate);
}
