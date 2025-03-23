package ru.myspar.service.dish;

import jakarta.validation.Valid;
import ru.myspar.dto.dish.DishCreationDto;
import ru.myspar.dto.dish.DishDto;

import java.util.List;

public interface DishService {
    DishDto createDish(@Valid DishCreationDto dishCreationDto);

    List<DishDto> getDishes();

    void deleteDish(int id);

    DishDto getDishById(int id);
}
