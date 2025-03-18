package ru.myspar.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.myspar.enums.Gender;
import ru.myspar.enums.Goal;

import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {

    @NotBlank(message = "Имя не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина имени должна быть от 2 до 250 символов.")
    private String name;

    @NotBlank(message = "Эл. почта не может быть пустой.")
    @Email
    private String email;

    @NotNull
    @Min(value = 0, message = "Возраст должен быть больше или равен 0 (полных лет).")
    @Max(value = 150, message = "Возраст должен быть меньше или равен 150.")
    private Integer age;

    @NotNull
    @DecimalMax(value = "251.0", message = "Рост не может быть больше 251 см.")
    @DecimalMin(value = "50.0", message = "Рост не может быть меньше 62,8 см.")
    private Double height;

    @NotNull
    @DecimalMax(value = "600.0", message = "Вес не может быть больше 600 кг.")
    @DecimalMin(value = "20.0", message = "Вес не может быть меньше 20 кг.")
    private Double weight;

    @NotBlank(message = "Пол не может быть пустым.")
    private String gender;

    @NotBlank(message = "Цель не может быть пустой.")
    private String goal;

    public Gender getGenderEnum() {
        try {
            return Gender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Недопустимое значение для пола: " + gender +
                    ". Допустимые значения: " + Arrays.toString(Gender.values()));
        }
    }

    public Goal getGoalEnum() {
        try {
            return Goal.valueOf(goal.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Недопустимое значение для цели: " + goal +
                    ". Допустимые значения: " + Arrays.toString(Goal.values()));
        }
    }
}
