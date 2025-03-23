package ru.myspar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.myspar.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Integer> {
    List<Meal> findByUserId(Integer userId);

    List<Meal> findByUserIdAndMealDateTimeBetween(Integer userId, LocalDateTime start, LocalDateTime end);
}