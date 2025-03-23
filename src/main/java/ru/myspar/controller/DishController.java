package ru.myspar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myspar.dto.dish.DishCreationDto;
import ru.myspar.dto.dish.DishDto;
import ru.myspar.service.dish.DishService;

import java.util.List;

@RestController
@RequestMapping(path = "/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<DishDto> createDish(@Valid @RequestBody DishCreationDto dishCreationDto) {
        DishDto dish = dishService.createDish(dishCreationDto);
        return new ResponseEntity<>(dish, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DishDto>> getDishes() {
        List<DishDto> dishes = dishService.getDishes();
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }

    @GetMapping("{dishId}")
    public ResponseEntity<DishDto> getDish(@PathVariable(name = "dishId") int id) {
        DishDto dishById = dishService.getDishById(id);
        return new ResponseEntity<>(dishById, HttpStatus.OK);

    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<Void> deleteDish(@PathVariable(name = "dishId") int dishId) {
        dishService.deleteDish(dishId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
