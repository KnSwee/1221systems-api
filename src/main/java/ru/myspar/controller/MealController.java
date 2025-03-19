package ru.myspar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.myspar.dto.meal.MealCreationDto;
import ru.myspar.dto.meal.MealDto;
import ru.myspar.service.meal.MealService;

import java.util.List;

@RestController
@RequestMapping(path = "/meals")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MealDto createMeal(@Valid @RequestBody MealCreationDto mealCreationDto,
                              @RequestHeader("X-User-Id") Integer userId) {
        return mealService.createMeal(mealCreationDto, userId);
    }

    @GetMapping
    public List<MealDto> getMealsByUserId(@RequestHeader("X-User-Id") Integer userId) {
        return mealService.getMealsById(userId);
    }

    @GetMapping("/meals/{mealId}")
    public MealDto getMealById(@PathVariable(name = "mealId") Integer mealId) {
        return mealService.getMealById(mealId);
    }

    @DeleteMapping("/meals/{mealId}")
    public boolean deleteMealById(@PathVariable(name = "mealId") Integer mealId) {
        return mealService.deleteMealById(mealId);
    }

}
