package ru.myspar.service.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myspar.dto.meal.MealWithCaloriesDto;
import ru.myspar.dto.report.ComplianceReportDto;
import ru.myspar.dto.report.DailyReportDto;
import ru.myspar.dto.report.MealHistoryDto;
import ru.myspar.exception.BadRequestException;
import ru.myspar.exception.NotFoundException;
import ru.myspar.model.Dish;
import ru.myspar.model.Meal;
import ru.myspar.model.User;
import ru.myspar.repository.DishRepository;
import ru.myspar.repository.MealRepository;
import ru.myspar.repository.UserRepository;
import ru.myspar.service.meal.MealDtoMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final MealRepository mealRepository;

    @Override
    public DailyReportDto generateDailyReport(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        LocalDate today = LocalDate.now();
        return generateDailyReportForDate(user, today);
    }

    private DailyReportDto generateDailyReportForDate(User user, LocalDate date) {
        List<Meal> meals = getTodayMeals(date, user.getId());

        List<MealWithCaloriesDto> mealWithCaloriesDtos = meals.stream()
                .map(MealDtoMapper::toMealWithCaloriesDto)
                .collect(Collectors.toList());

        int totalCalories = mealWithCaloriesDtos.stream()
                .mapToInt(MealWithCaloriesDto::getMealCalories)
                .sum();

        DailyReportDto dailyReportDto = new DailyReportDto();
        dailyReportDto.setUserId(user.getId());
        dailyReportDto.setUserName(user.getName());
        dailyReportDto.setReportDate(date);
        dailyReportDto.setMeals(mealWithCaloriesDtos);
        dailyReportDto.setTotalCalories(totalCalories);
        return dailyReportDto;
    }

    @Override
    public ComplianceReportDto checkCompliance(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        LocalDate today = LocalDate.now();
        List<Meal> meals = getTodayMeals(today, user.getId());
        int totalCalories = meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToInt(Dish::getCalories)
                .sum();

        return getComplianceReportDto(totalCalories, user);
    }

    @Override
    public MealHistoryDto getMealHistory(Integer userId, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден."));

        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Дата начала не может быть после даты окончания.");
        }

        Map<LocalDate, DailyReportDto> dailyReports = new HashMap<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            DailyReportDto dailyReport = generateDailyReportForDate(user, currentDate);
            dailyReports.put(currentDate, dailyReport);
            currentDate = currentDate.plusDays(1);
        }

        MealHistoryDto mealHistoryDto = new MealHistoryDto();
        mealHistoryDto.setUserId(userId);
        mealHistoryDto.setUserName(user.getName());
        mealHistoryDto.setDailyReports(dailyReports);

        return mealHistoryDto;
    }

    private List<Meal> getTodayMeals(LocalDate today, Integer userId) {
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();
        return mealRepository.findByUserIdAndMealDateTimeBetween(userId, startOfDay, endOfDay);
    }

    private static ComplianceReportDto getComplianceReportDto(int totalCalories, User user) {
        boolean isCaloriesOver = totalCalories > user.getDailyCaloriesNorm();

        ComplianceReportDto complianceReportDto = new ComplianceReportDto();
        complianceReportDto.setUserId(user.getId());
        complianceReportDto.setUserName(user.getName());
        complianceReportDto.setDailyCaloriesNorm(user.getDailyCaloriesNorm());
        complianceReportDto.setCaloriesConsumed(totalCalories);
        complianceReportDto.setCompliant(isCaloriesOver);
        complianceReportDto.setCaloriesOverUnder(totalCalories - user.getDailyCaloriesNorm());
        return complianceReportDto;
    }
}
