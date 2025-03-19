package ru.myspar.dto.meal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.myspar.dto.dish.DishDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealCreationDto {

    @NotNull
    private LocalDateTime mealDateTime;

    private List<DishDto> dishes;
}
