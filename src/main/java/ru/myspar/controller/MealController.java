package ru.myspar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MealDto> createMeal(@Valid @RequestBody MealCreationDto mealCreationDto) {
        MealDto meal = mealService.createMeal(mealCreationDto);
        return new ResponseEntity<>(meal, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MealDto>> getMealsByUserId(@RequestHeader("X-User-Id") Integer userId) {
        List<MealDto> mealsByUserId = mealService.getMealsByUserId(userId);
        return new ResponseEntity<>(mealsByUserId, HttpStatus.OK);
    }

    @GetMapping("/{mealId}")
    public ResponseEntity<MealDto> getMealById(@PathVariable(name = "mealId") Integer mealId) {
        MealDto mealById = mealService.getMealById(mealId);
        return new ResponseEntity<>(mealById, HttpStatus.OK);
    }

    @DeleteMapping("{mealId}")
    public ResponseEntity<Void> deleteMealById(@PathVariable(name = "mealId") Integer mealId) {
        mealService.deleteMealById(mealId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
