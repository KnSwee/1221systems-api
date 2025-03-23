package ru.myspar.service.meal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myspar.dto.meal.MealCreationDto;
import ru.myspar.dto.meal.MealDto;
import ru.myspar.exception.NotFoundException;
import ru.myspar.model.Dish;
import ru.myspar.model.Meal;
import ru.myspar.model.User;
import ru.myspar.repository.DishRepository;
import ru.myspar.repository.MealRepository;
import ru.myspar.repository.UserRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;


    public MealDto createMeal(MealCreationDto mealCreationDto) {
        User user = userRepository.findById(mealCreationDto.getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователь с id: " + mealCreationDto.getUserId() + " не найден."));

        List<Dish> dishes = dishRepository.findAllById(mealCreationDto.getDishIds());

        if (dishes.size() != mealCreationDto.getDishIds().size()) {
            throw new NotFoundException("Не все блюда были найдены.");
        }

        Meal meal = new Meal();
        meal.setUser(user);
        meal.setMealDateTime(mealCreationDto.getMealDateTime());
        meal.setDishes(dishes);

        Meal savedMeal = mealRepository.save(meal);

        return MealDtoMapper.toMealDto(savedMeal);
    }

    @Override
    public List<MealDto> getMealsByUserId(Integer userId) {
        List<Meal> meals = mealRepository.findByUserId(userId);
        return MealDtoMapper.toMealDto(meals);
    }

    @Override
    public MealDto getMealById(Integer mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new NotFoundException("Прием пищи с id: " + mealId + " не найден."));
        return MealDtoMapper.toMealDto(meal);
    }

    @Override
    public void deleteMealById(Integer mealId) {
        if (!mealRepository.existsById(mealId)) {
            throw new NotFoundException("Прием пищи с id: " + mealId + " не найден.");
        }
        mealRepository.deleteById(mealId);
    }
}
