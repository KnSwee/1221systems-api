package ru.myspar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.myspar.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Integer> {
}