package ru.myspar.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealHistoryDto {
    private Integer userId;
    private String userName;
    private Map<LocalDate, DailyReportDto> dailyReports;
}
