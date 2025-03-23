package ru.myspar.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.myspar.dto.meal.MealWithCaloriesDto;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyReportDto {
    private Integer userId;
    private String userName;
    private LocalDate reportDate;
    private Integer totalCalories;
    private List<MealWithCaloriesDto> meals;
}
