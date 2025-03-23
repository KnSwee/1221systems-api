package ru.myspar.dto.meal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealCreationDto {

    @NotNull
    private Integer userId;

    @NotNull
    private LocalDateTime mealDateTime;

    @NotNull(message = "Список блюд в приеме пищи не может быть пустым.")
    private List<Integer> dishIds;
}
