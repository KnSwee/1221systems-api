package ru.myspar.service.dish;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myspar.dto.dish.DishCreationDto;
import ru.myspar.dto.dish.DishDto;
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
    public boolean deleteDish(int id) {
        if (!dishRepository.existsById(id)) {
            return false;
        }
        dishRepository.deleteById(id);
        return !dishRepository.existsById(id);
    }
}
