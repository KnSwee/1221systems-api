package ru.myspar.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.myspar.enums.Gender;
import ru.myspar.enums.Goal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {

    @NotBlank(message = "Имя не может быть пустым.")
    @Size(min = 2, max = 250, message = "Длина имени должна быть от 2 до 250 символов.")
    private String name;

    @NotBlank(message = "Эл. почта не может быть пустой.")
    @Email(message = "Email введен некорректно.")
    private String email;

    @NotNull
    @Min(value = 0, message = "Возраст должен быть больше или равен 0 (полных лет).")
    @Max(value = 150, message = "Возраст должен быть меньше или равен 150.")
    private Integer age;

    @NotNull
    @DecimalMax(value = "600.0", message = "Вес не может быть больше 600 кг.")
    @DecimalMin(value = "20.0", message = "Вес не может быть меньше 20 кг.")
    private Double weight;

    @NotNull
    @DecimalMax(value = "251.0", message = "Рост не может быть больше 251 см.")
    @DecimalMin(value = "50.0", message = "Рост не может быть меньше 62,8 см.")
    private Double height;

    @NotNull(message = "Пол не может быть пустым.")
    private Gender gender;

    @NotNull(message = "Цель не может быть пустой.")
    private Goal goal;
}
