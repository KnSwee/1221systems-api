package ru.myspar.service.meal;

import jakarta.validation.Valid;
import ru.myspar.dto.meal.MealCreationDto;
import ru.myspar.dto.meal.MealDto;

import java.util.List;

public interface MealService {
    MealDto createMeal(@Valid MealCreationDto mealCreationDto);

    List<MealDto> getMealsByUserId(Integer userId);

    MealDto getMealById(Integer mealId);

    void deleteMealById(Integer mealId);
}
