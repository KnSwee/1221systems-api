package ru.myspar.dto.meal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.myspar.dto.dish.DishDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDto {

    private Integer id;

    private Integer userId;

    private LocalDateTime mealDateTime;

    private List<DishDto> dishes;
}
