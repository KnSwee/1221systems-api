package ru.myspar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public DishDto createDish(@Valid @RequestBody DishCreationDto dishCreationDto) {
        return dishService.createDish(dishCreationDto);
    }

    @GetMapping
    public List<DishDto> getDishes() {
        return dishService.getDishes();
    }

    @DeleteMapping
    public boolean deleteDishes(@RequestParam int id) {
        return dishService.deleteDish(id);
    }
}
