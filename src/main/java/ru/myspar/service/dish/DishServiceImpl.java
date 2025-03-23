package ru.myspar.service.dish;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myspar.dto.dish.DishCreationDto;
import ru.myspar.dto.dish.DishDto;
import ru.myspar.exception.NotFoundException;
import ru.myspar.model.Dish;
import ru.myspar.repository.DishRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;

    @Override
    public DishDto createDish(DishCreationDto dishCreationDto) {
        Dish dish = dishRepository.save(DishDtoMapper.toDish(dishCreationDto));
        return DishDtoMapper.toDishDto(dish);
    }

    @Override
    public List<DishDto> getDishes() {
        return DishDtoMapper.toDishDto(dishRepository.findAll());
    }

    @Override
    public DishDto getDishById(int id) {
        Dish dish = dishRepository.findById(id).orElseThrow(() -> new NotFoundException("Блюда с id " + id + "не существует"));
        return DishDtoMapper.toDishDto(dish);
    }

    @Override
    public void deleteDish(int id) {
        if (!dishRepository.existsById(id)) {
            throw new NotFoundException("Блюдо с id: " + id + " не найдено.");
        }
        dishRepository.deleteById(id);
    }
}
