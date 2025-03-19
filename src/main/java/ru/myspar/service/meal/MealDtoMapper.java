package ru.myspar.service.meal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.myspar.dto.meal.MealDto;
import ru.myspar.model.Meal;
import ru.myspar.model.User;
import ru.myspar.service.dish.DishDtoMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MealDtoMapper {

    public static Meal toMeal(MealDto mealDto, User user) {
        Meal meal = new Meal();
        meal.setMealDateTime(mealDto.getMealDateTime());
        meal.setUser(user);
        meal.setDishes(DishDtoMapper.toDish(mealDto.getDishes()));
    }

}
