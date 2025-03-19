package ru.myspar.service.dish;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.myspar.dto.dish.DishCreationDto;
import ru.myspar.dto.dish.DishDto;
import ru.myspar.model.Dish;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DishDtoMapper {

    public static Dish toDish(DishCreationDto dishCreationDto) {
        Dish dish = new Dish();
        dish.setName(dishCreationDto.getName());
        dish.setCalories(dishCreationDto.getCalories());
        dish.setProteins(dishCreationDto.getProteins());
        dish.setCarbohydrates(dishCreationDto.getCarbohydrates());
        dish.setFats(dishCreationDto.getFats());
        return dish;
    }

    public static Dish toDish(DishDto dishDto) {
        Dish dish = new Dish();
        dish.setId(dishDto.getId());
        dish.setName(dishDto.getName());
        dish.setCalories(dishDto.getCalories());
        dish.setProteins(dishDto.getProteins());
        dish.setCarbohydrates(dishDto.getCarbohydrates());
        dish.setFats(dishDto.getFats());
        return dish;
    }

    public static List<Dish> toDish(List<DishDto> dishDtoList) {
        List<Dish> dishList = new ArrayList<>();
        for (DishDto dishDto : dishDtoList) {
            dishList.add(toDish(dishDto));
        }
        return dishList;
    }

    public static DishDto toDishDto(Dish dish) {
        DishDto dishDto = new DishDto();
        dishDto.setId(dish.getId());
        dishDto.setName(dish.getName());
        dishDto.setCalories(dish.getCalories());
        dishDto.setProteins(dish.getProteins());
        dishDto.setCarbohydrates(dish.getCarbohydrates());
        dishDto.setFats(dish.getFats());
        return dishDto;
    }

    public static List<DishDto> toDishDto(List<Dish> dishes) {
        List<DishDto> dishDtos = new ArrayList<>();
        for (Dish dish : dishes) {
            dishDtos.add(toDishDto(dish));
        }
        return dishDtos;
    }
}
