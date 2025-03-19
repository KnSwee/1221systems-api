package ru.myspar.service.meal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myspar.dto.meal.MealCreationDto;
import ru.myspar.dto.meal.MealDto;
import ru.myspar.model.Dish;
import ru.myspar.model.Meal;
import ru.myspar.model.User;
import ru.myspar.repository.DishRepository;
import ru.myspar.repository.MealRepository;
import ru.myspar.repository.UserRepository;
import ru.myspar.service.dish.DishDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository; // Add UserRepository


    @Override
    public MealDto createMeal(MealCreationDto mealCreationDto, Integer userId) { // Pass userId
        logger.info("Создание нового приема пищи с данными: {} для пользователя с id: {}", mealCreationDto, userId);

        // 1. Get the User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с id " + userId + " не найден."));

        // 2. Convert DishDto to Dish (and fetch from DB if needed)
        List<Dish> dishes = mealCreationDto.getDishes().stream()
                .map(dishDto -> {
                    // Assuming DishDto has an 'id' field
                    if (dishDto.getId() != null) {
                        return dishRepository.findById(dishDto.getId())
                                .orElseThrow(() -> new IllegalArgumentException("Блюдо с id " + dishDto.getId() + " не найдено."));
                    } else {
                        // Handle the case where DishDto doesn't have an ID (create new Dish?)
                        // Example:
                        Dish newDish = DishDtoMapper.toDish(dishDto);  // Use your mapper
                        return dishRepository.save(newDish);
                    }
                })
                .collect(Collectors.toList());


        // 3. Create the Meal Entity
        Meal meal = new Meal();
        meal.setMealDateTime(mealCreationDto.getMealDateTime());
        meal.setDishes(dishes);
        meal.setUser(user); // Set the User


        // 4. Save the Meal
        Meal savedMeal = mealRepository.save(meal);
        logger.info("Прием пищи успешно создан с id: {}", savedMeal.getId());


        // 5. Convert to Dto and return
        return MealDtoMapper.toMealDto(savedMeal);
    }

    @Override
    public List<MealDto> getMealsById(Integer userId) {
        return List.of();
    }

    @Override
    public MealDto getMealById(Integer mealId) {
        return null;
    }

    @Override
    public boolean deleteMealById(Integer mealId) {
        return false;
    }
}
