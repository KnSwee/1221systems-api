package dish;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.myspar.controller.DishController;
import ru.myspar.dto.dish.DishCreationDto;
import ru.myspar.dto.dish.DishDto;
import ru.myspar.service.dish.DishService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DishControllerTest {

    @Mock
    private DishService dishService;

    @InjectMocks
    private DishController dishController;

    @Test
    void createDishTest() {
        DishCreationDto dishCreationDto = new DishCreationDto();
        dishCreationDto.setName("Тестовое Блюдо");
        dishCreationDto.setCalories(100);
        dishCreationDto.setProteins(10.0);
        dishCreationDto.setFats(5.0);
        dishCreationDto.setCarbohydrates(15.0);

        DishDto expectedDishDto = new DishDto();
        expectedDishDto.setId(1);
        expectedDishDto.setName("Тестовое Блюдо");
        expectedDishDto.setCalories(100);
        expectedDishDto.setProteins(10.0);
        expectedDishDto.setFats(5.0);
        expectedDishDto.setCarbohydrates(15.0);

        when(dishService.createDish(dishCreationDto)).thenReturn(expectedDishDto);

        ResponseEntity<DishDto> responseEntity = dishController.createDish(dishCreationDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedDishDto, responseEntity.getBody());
        verify(dishService, times(1)).createDish(dishCreationDto);
    }

    @Test
    void getDishesTest() {
        List<DishDto> expectedDishes = new ArrayList<>();
        DishDto dish1 = new DishDto();
        dish1.setId(1);
        dish1.setName("Блюдо 1");

        DishDto dish2 = new DishDto();
        dish2.setId(2);
        dish2.setName("Блюдо 2");

        expectedDishes.add(dish1);
        expectedDishes.add(dish2);

        when(dishService.getDishes()).thenReturn(expectedDishes);

        ResponseEntity<List<DishDto>> responseEntity = dishController.getDishes();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedDishes, responseEntity.getBody());
        verify(dishService, times(1)).getDishes();
    }

    @Test
    void getDishTest() {
        int dishId = 1;
        DishDto expectedDishDto = new DishDto();
        expectedDishDto.setId(dishId);
        expectedDishDto.setName("Тестовое Блюдо");

        when(dishService.getDishById(dishId)).thenReturn(expectedDishDto);

        ResponseEntity<DishDto> responseEntity = dishController.getDish(dishId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedDishDto, responseEntity.getBody());
        verify(dishService, times(1)).getDishById(dishId);
    }

    @Test
    void deleteDishTest() {
        int dishId = 1;

        ResponseEntity<Void> responseEntity = dishController.deleteDish(dishId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(dishService, times(1)).deleteDish(dishId);
    }
}
