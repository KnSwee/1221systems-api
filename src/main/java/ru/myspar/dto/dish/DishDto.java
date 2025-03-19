package ru.myspar.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {

    private Integer id;

    private String name;

    private int calories;

    private double proteins;

    private double fats;

    private double carbohydrates;
}
