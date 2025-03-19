package ru.myspar.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.myspar.enums.Goal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private int age;
    private double weight;
    private double height;
    private String gender;
    private String goal;
    private double DailyCaloriesNorm;

}
