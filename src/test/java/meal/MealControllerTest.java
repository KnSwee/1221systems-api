package meal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.myspar.controller.MealController;
import ru.myspar.dto.meal.MealCreationDto;
import ru.myspar.dto.meal.MealDto;
import ru.myspar.service.meal.MealService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealControllerTest {

    @Mock
    private MealService mealService;

    @InjectMocks
    private MealController mealController;

    @Test
    void createMealTest() {
        MealCreationDto mealCreationDto = new MealCreationDto();
        mealCreationDto.setUserId(1);
        mealCreationDto.setMealDateTime(LocalDateTime.now());
        mealCreationDto.setDishIds(List.of(1, 2));

        MealDto expectedMealDto = new MealDto();
        expectedMealDto.setId(1);
        expectedMealDto.setUserId(1);
        expectedMealDto.setMealDateTime(mealCreationDto.getMealDateTime());

        when(mealService.createMeal(mealCreationDto)).thenReturn(expectedMealDto);

        ResponseEntity<MealDto> responseEntity = mealController.createMeal(mealCreationDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedMealDto, responseEntity.getBody());
        verify(mealService, times(1)).createMeal(mealCreationDto);
    }

    @Test
    void getMealsByUserIdTest() {
        Integer userId = 1;
        List<MealDto> expectedMeals = new ArrayList<>();
        MealDto meal1 = new MealDto();
        meal1.setId(1);
        meal1.setUserId(userId);

        MealDto meal2 = new MealDto();
        meal2.setId(2);
        meal2.setUserId(userId);

        expectedMeals.add(meal1);
        expectedMeals.add(meal2);

        when(mealService.getMealsByUserId(userId)).thenReturn(expectedMeals);

        ResponseEntity<List<MealDto>> responseEntity = mealController.getMealsByUserId(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedMeals, responseEntity.getBody());
        verify(mealService, times(1)).getMealsByUserId(userId);
    }

    @Test
    void getMealByIdTest() {
        Integer mealId = 1;
        MealDto expectedMealDto = new MealDto();
        expectedMealDto.setId(mealId);
        expectedMealDto.setUserId(1);

        when(mealService.getMealById(mealId)).thenReturn(expectedMealDto);

        ResponseEntity<MealDto> responseEntity = mealController.getMealById(mealId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedMealDto, responseEntity.getBody());
        verify(mealService, times(1)).getMealById(mealId);
    }

    @Test
    void deleteMealByIdTest() {
        Integer mealId = 1;

        ResponseEntity<Void> responseEntity = mealController.deleteMealById(mealId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(mealService, times(1)).deleteMealById(mealId);
    }
}
