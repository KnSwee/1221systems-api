package ru.myspar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.myspar.model.Meal;

public interface MealRepository extends JpaRepository<Meal, Integer> {
}