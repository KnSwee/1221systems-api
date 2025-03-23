package dish;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.myspar.dto.dish.DishCreationDto;
import ru.myspar.dto.dish.DishDto;
import ru.myspar.exception.NotFoundException;
import ru.myspar.model.Dish;
import ru.myspar.repository.DishRepository;
import ru.myspar.service.dish.DishServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishServiceImpl dishService;

    @Test
    void createDishTest() {
        DishCreationDto dishCreationDto = new DishCreationDto();
        dishCreationDto.setName("Тестовое Блюдо");
        dishCreationDto.setCalories(100);

        Dish dishToSave = new Dish();
        dishToSave.setName(dishCreationDto.getName());
        dishToSave.setCalories(dishCreationDto.getCalories());

        Dish savedDish = new Dish();
        savedDish.setId(1);
        savedDish.setName(dishCreationDto.getName());
        savedDish.setCalories(dishCreationDto.getCalories());

        when(dishRepository.save(any(Dish.class))).thenReturn(savedDish);

        DishDto result = dishService.createDish(dishCreationDto);

        assertEquals(1, result.getId());
        assertEquals("Тестовое Блюдо", result.getName());
        assertEquals(100, result.getCalories());
        verify(dishRepository, times(1)).save(any(Dish.class));
    }

    @Test
    void getDishesTest() {
        List<Dish> dishes = new ArrayList<>();
        Dish dish1 = new Dish();
        dish1.setId(1);
        dish1.setName("Блюдо 1");

        Dish dish2 = new Dish();
        dish2.setId(2);
        dish2.setName("Блюдо 2");

        dishes.add(dish1);
        dishes.add(dish2);

        when(dishRepository.findAll()).thenReturn(dishes);

        List<DishDto> result = dishService.getDishes();

        assertEquals(2, result.size());
        assertEquals("Блюдо 1", result.get(0).getName());
        assertEquals("Блюдо 2", result.get(1).getName());
        verify(dishRepository, times(1)).findAll();
    }

    @Test
    void getDishByIdTest() {
        int dishId = 1;
        Dish dish = new Dish();
        dish.setId(dishId);
        dish.setName("Тестовое Блюдо");

        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        DishDto result = dishService.getDishById(dishId);

        assertEquals(dishId, result.getId());
        assertEquals("Тестовое Блюдо", result.getName());
        verify(dishRepository, times(1)).findById(dishId);
    }

    @Test
    void getDishByIdNonExistingDishTest() {
        int dishId = 1;
        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> dishService.getDishById(dishId));
        verify(dishRepository, times(1)).findById(dishId);
    }

    @Test
    void deleteDishTest() {
        int dishId = 1;
        when(dishRepository.existsById(dishId)).thenReturn(true);

        dishService.deleteDish(dishId);

        // Assert
        verify(dishRepository, times(1)).existsById(dishId);
        verify(dishRepository, times(1)).deleteById(dishId);
    }

    @Test
    void deleteDishNonExistingDishTest() {
        int dishId = 1;
        when(dishRepository.existsById(dishId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> dishService.deleteDish(dishId));
        verify(dishRepository, times(1)).existsById(dishId);
        verify(dishRepository, never()).deleteById(anyInt());
    }
}
