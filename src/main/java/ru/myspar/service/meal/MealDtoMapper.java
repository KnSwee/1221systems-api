package ru.myspar.service.meal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.myspar.dto.meal.MealDto;
import ru.myspar.dto.meal.MealWithCaloriesDto;
import ru.myspar.model.Dish;
import ru.myspar.model.Meal;
import ru.myspar.model.User;
import ru.myspar.service.dish.DishDtoMapper;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MealDtoMapper {

    public static Meal toMeal(MealDto mealDto, User user) {
        Meal meal = new Meal();
        meal.setMealDateTime(mealDto.getMealDateTime());
        meal.setUser(user);
        meal.setDishes(DishDtoMapper.toDish(mealDto.getDishes()));
        return meal;
    }

    public static MealDto toMealDto(Meal meal) {
        MealDto mealDto = new MealDto();
        mealDto.setMealDateTime(meal.getMealDateTime());
        mealDto.setId(meal.getId());
        mealDto.setDishes(DishDtoMapper.toDishDto(meal.getDishes()));
        mealDto.setUserId(meal.getUser().getId());
        return mealDto;
    }

    public static List<MealDto> toMealDto(List<Meal> meals) {
        List<MealDto> mealDtos = new ArrayList<>();
        for (Meal meal : meals) {
            mealDtos.add(toMealDto(meal));
        }
        return mealDtos;
    }

    public static MealWithCaloriesDto toMealWithCaloriesDto(Meal meal) {
        MealWithCaloriesDto mealDto = new MealWithCaloriesDto();
        mealDto.setMealDateTime(meal.getMealDateTime());
        mealDto.setId(meal.getId());
        mealDto.setDishes(DishDtoMapper.toDishDto(meal.getDishes()));
        mealDto.setUserId(meal.getUser().getId());
        int mealCalories = meal.getDishes().stream()
                .mapToInt(Dish::getCalories)
                .sum();
        mealDto.setMealCalories(mealCalories);
        return mealDto;
    }
}
