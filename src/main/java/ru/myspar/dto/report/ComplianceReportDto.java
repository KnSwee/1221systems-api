package ru.myspar.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceReportDto {
    private Integer userId;
    private String userName;
    private Integer dailyCaloriesNorm;
    private Integer caloriesConsumed;
    private boolean isCompliant;
    private Integer caloriesOverUnder;
}
