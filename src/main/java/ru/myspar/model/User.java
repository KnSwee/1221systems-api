package ru.myspar.model;

import jakarta.persistence.*;
import lombok.*;
import ru.myspar.enums.Gender;
import ru.myspar.enums.Goal;

@Table(name = "users")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "weight")
    private double weight;

    @Column(name = "height")
    private double height;

    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal")
    private Goal goal;

    @Setter
    @Column(name = "daily_calories_norm")
    private int dailyCaloriesNorm;

    public Integer calculateDailyCaloriesNorm() {
        double CaloriesNorm;
        if (this.gender == Gender.MALE) {
            CaloriesNorm = 88.362 + (13.397 * this.weight) + (4.799 * this.height) - (5.677 * this.age);
        } else {
            CaloriesNorm = 447.593 + (9.247 * this.weight) + (3.098 * this.height) - (4.330 * this.age);
        }

        return (int) Math.round(CaloriesNorm);
    }

    public void updateDailyCaloriesNorm() {
        this.dailyCaloriesNorm = calculateDailyCaloriesNorm();
    }
}
