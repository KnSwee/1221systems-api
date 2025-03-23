package meal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.myspar.dto.meal.MealCreationDto;
import ru.myspar.dto.meal.MealDto;
import ru.myspar.exception.NotFoundException;
import ru.myspar.model.Dish;
import ru.myspar.model.Meal;
import ru.myspar.model.User;
import ru.myspar.repository.DishRepository;
import ru.myspar.repository.MealRepository;
import ru.myspar.repository.UserRepository;
import ru.myspar.service.meal.MealServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MealServiceImpl mealService;

    @Test
    void createMealTest() {
        MealCreationDto mealCreationDto = new MealCreationDto();
        mealCreationDto.setUserId(1);
        mealCreationDto.setMealDateTime(LocalDateTime.now());
        mealCreationDto.setDishIds(List.of(1, 2));

        User user = new User();
        user.setId(1);

        List<Dish> dishes = new ArrayList<>();
        Dish dish1 = new Dish();
        dish1.setId(1);
        dishes.add(dish1);
        Dish dish2 = new Dish();
        dish2.setId(2);
        dishes.add(dish2);

        Meal savedMeal = new Meal();
        savedMeal.setId(1);
        savedMeal.setUser(user);
        savedMeal.setMealDateTime(mealCreationDto.getMealDateTime());
        savedMeal.setDishes(dishes);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(dishRepository.findAllById(mealCreationDto.getDishIds())).thenReturn(dishes);
        when(mealRepository.save(any(Meal.class))).thenReturn(savedMeal);

        MealDto result = mealService.createMeal(mealCreationDto);

        assertEquals(1, result.getId());
        assertEquals(1, result.getUserId());
        assertEquals(mealCreationDto.getMealDateTime(), result.getMealDateTime());
        verify(userRepository, times(1)).findById(1);
        verify(dishRepository, times(1)).findAllById(mealCreationDto.getDishIds());
        verify(mealRepository, times(1)).save(any(Meal.class));
    }

    @Test
    void createMealNonExistingUserTest() {
        MealCreationDto mealCreationDto = new MealCreationDto();
        mealCreationDto.setUserId(1);
        mealCreationDto.setMealDateTime(LocalDateTime.now());
        mealCreationDto.setDishIds(List.of(1, 2));

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> mealService.createMeal(mealCreationDto));
        verify(userRepository, times(1)).findById(1);
        verify(dishRepository, never()).findAllById(any());
        verify(mealRepository, never()).save(any());
    }

    @Test
    void createMealNotAllDishesFoundTest() {
        MealCreationDto mealCreationDto = new MealCreationDto();
        mealCreationDto.setUserId(1);
        mealCreationDto.setMealDateTime(LocalDateTime.now());
        mealCreationDto.setDishIds(List.of(1, 2, 3));

        User user = new User();
        user.setId(1);

        List<Dish> dishes = new ArrayList<>();
        Dish dish1 = new Dish();
        dish1.setId(1);
        dishes.add(dish1);
        Dish dish2 = new Dish();
        dish2.setId(2);
        dishes.add(dish2);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(dishRepository.findAllById(mealCreationDto.getDishIds())).thenReturn(dishes);

        assertThrows(NotFoundException.class, () -> mealService.createMeal(mealCreationDto));
        verify(userRepository, times(1)).findById(1);
        verify(dishRepository, times(1)).findAllById(mealCreationDto.getDishIds());
        verify(mealRepository, never()).save(any());
    }

    @Test
    void getMealsByUserIdTest() {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        List<Meal> meals = new ArrayList<>();
        Meal meal1 = new Meal();
        meal1.setId(1);
        meal1.setUser(user);
        meal1.setDishes(new ArrayList<>());
        Meal meal2 = new Meal();
        meal2.setId(2);
        meal2.setUser(user);
        meal2.setDishes(new ArrayList<>());

        meals.add(meal1);
        meals.add(meal2);

        when(mealRepository.findByUserId(userId)).thenReturn(meals);

        List<MealDto> result = mealService.getMealsByUserId(userId);

        assertEquals(2, result.size());
        assertEquals(userId, result.get(0).getUserId());
        assertEquals(userId, result.get(1).getUserId());
        verify(mealRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getMealByIdTest() {
        Integer mealId = 1;
        Meal meal = new Meal();
        meal.setId(mealId);
        meal.setUser(new User());
        meal.getUser().setId(1);
        meal.setDishes(new ArrayList<>());

        when(mealRepository.findById(mealId)).thenReturn(Optional.of(meal));

        MealDto result = mealService.getMealById(mealId);

        assertEquals(mealId, result.getId());
        assertEquals(1, result.getUserId());
        verify(mealRepository, times(1)).findById(mealId);
    }

    @Test
    void getMealByIdNonExistingMealTest() {
        Integer mealId = 1;
        when(mealRepository.findById(mealId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> mealService.getMealById(mealId));
        verify(mealRepository, times(1)).findById(mealId);
    }

    @Test
    void deleteMealByIdTest() {
        Integer mealId = 1;
        when(mealRepository.existsById(mealId)).thenReturn(true);

        mealService.deleteMealById(mealId);

        verify(mealRepository, times(1)).existsById(mealId);
        verify(mealRepository, times(1)).deleteById(mealId);
    }

    @Test
    void deleteMealByIdNonExistingMealTest() {
        Integer mealId = 1;
        when(mealRepository.existsById(mealId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> mealService.deleteMealById(mealId));
        verify(mealRepository, times(1)).existsById(mealId);
        verify(mealRepository, never()).deleteById(mealId);
    }
}
