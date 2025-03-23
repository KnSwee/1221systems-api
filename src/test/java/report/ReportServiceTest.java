package report;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import ru.myspar.service.report.ReportServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void generateDailyReportTest() {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        user.setName("Test User");

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        List<Meal> meals = new ArrayList<>();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mealRepository.findByUserIdAndMealDateTimeBetween(userId, startOfDay, endOfDay)).thenReturn(meals);

        DailyReportDto result = reportService.generateDailyReport(userId);

        assertEquals(userId, result.getUserId());
        assertEquals("Test User", result.getUserName());
        assertEquals(today, result.getReportDate());
        assertEquals(0, result.getMeals().size());
        assertEquals(0, result.getTotalCalories());

        verify(userRepository, times(1)).findById(userId);
        verify(mealRepository, times(1)).findByUserIdAndMealDateTimeBetween(userId, startOfDay, endOfDay);
    }

    @Test
    void generateDailyReportNonExistingUserTest() {
        Integer userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reportService.generateDailyReport(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(mealRepository, never()).findByUserIdAndMealDateTimeBetween(any(), any(), any());
    }

    @Test
    void checkComplianceTest() {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        user.setName("Test User");
        user.setDailyCaloriesNorm(2000);

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        List<Meal> meals = new ArrayList<>();
        Meal meal1 = new Meal();
        Dish dish = new Dish();
        dish.setCalories(500);
        meal1.setDishes(List.of(dish));
        meals.add(meal1);

        Meal meal2 = new Meal();
        Dish dish2 = new Dish();
        dish2.setCalories(300);
        meal2.setDishes(List.of(dish2));
        meals.add(meal2);


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mealRepository.findByUserIdAndMealDateTimeBetween(userId, startOfDay, endOfDay)).thenReturn(meals);

        ComplianceReportDto result = reportService.checkCompliance(userId);

        assertEquals(userId, result.getUserId());
        assertEquals("Test User", result.getUserName());
        assertEquals(2000, result.getDailyCaloriesNorm());
        assertEquals(800, result.getCaloriesConsumed());
        assertFalse(result.isCompliant());
        assertEquals(-1200, result.getCaloriesOverUnder());
        verify(userRepository, times(1)).findById(userId);
        verify(mealRepository, times(1)).findByUserIdAndMealDateTimeBetween(userId, startOfDay, endOfDay);
    }

    @Test
    void checkComplianceNonExistingUserTest() {
        Integer userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reportService.checkCompliance(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(mealRepository, never()).findByUserIdAndMealDateTimeBetween(any(), any(), any());
    }

    @Test
    void getMealHistoryTest() {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        user.setName("Test User");

        LocalDate startDate = LocalDate.now().minusDays(2);
        LocalDate endDate = LocalDate.now();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mealRepository.findByUserIdAndMealDateTimeBetween(eq(userId), any(), any())).thenReturn(new ArrayList<>());

        MealHistoryDto result = reportService.getMealHistory(userId, startDate, endDate);

        assertEquals(userId, result.getUserId());
        assertEquals("Test User", result.getUserName());
        assertNotNull(result.getDailyReports());
        assertEquals(3, result.getDailyReports().size());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getMealHistoryNonExistingUserTest() {
        Integer userId = 1;
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> reportService.getMealHistory(userId, startDate, endDate));

        verify(userRepository, times(1)).findById(userId);
        verify(mealRepository, never()).findByUserIdAndMealDateTimeBetween(any(), any(), any());
    }

    @Test
    void getMealHistoryStartDateAfterEndDateTest() {
        Integer userId = 1;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(7);

        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> reportService.getMealHistory(userId, startDate, endDate));

        verify(userRepository, times(1)).findById(userId);
        verify(mealRepository, never()).findByUserIdAndMealDateTimeBetween(any(), any(), any());
    }
}