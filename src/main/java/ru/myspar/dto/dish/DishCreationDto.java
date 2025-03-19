package ru.myspar.dto.dish;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishCreationDto {

    @NotBlank(message = "Название не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина названия должна быть от 2 до 250 символов.")
    private String name;

    @NotNull
    @Min(value = 0, message = "Количество калорий не должно быть меньше 0.")
    @Max(value = 10000, message = "Количество калорий не должно быть больше 10000.")
    private Integer calories;

    @NotNull
    @DecimalMax(value = "100.0", message = "Количество белков не должно быть больше 100")
    @DecimalMin(value = "0.0", message = "Количество белков не должно быть меньше 0.")
    private double proteins;

    @NotNull
    @DecimalMax(value = "100.0", message = "Количество жиров не должно быть больше 100")
    @DecimalMin(value = "0.0", message = "Количество жиров не должно быть меньше 0.")
    private double fats;

    @NotNull
    @DecimalMax(value = "100.0", message = "Количество углеводов не должно быть больше 100")
    @DecimalMin(value = "0.0", message = "Количество углеводов не должно быть меньше 0.")
    private double carbohydrates;
}
