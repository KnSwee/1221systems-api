package ru.myspar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myspar.dto.report.ComplianceReportDto;
import ru.myspar.dto.report.DailyReportDto;
import ru.myspar.dto.report.MealHistoryDto;
import ru.myspar.service.report.ReportService;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/daily/{userId}")
    public ResponseEntity<DailyReportDto> getDailyReport(@PathVariable(name = "userId") Integer userId) {
        DailyReportDto report = reportService.generateDailyReport(userId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/compliance/{userId}")
    public ResponseEntity<ComplianceReportDto> getComplianceReport(@PathVariable(name = "userId") Integer userId) {
        ComplianceReportDto report = reportService.checkCompliance(userId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<MealHistoryDto> getMealHistory(@PathVariable(name = "userId") Integer userId,
                                                         @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                         @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        MealHistoryDto mealHistory = reportService.getMealHistory(userId, startDate, endDate);
        return ResponseEntity.ok(mealHistory);
    }
}