package ru.myspar.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myspar.dto.user.UserCreationDto;
import ru.myspar.dto.user.UserDto;
import ru.myspar.exception.DuplicateEmailException;
import ru.myspar.exception.NotFoundException;
import ru.myspar.model.User;
import ru.myspar.repository.UserRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;

    @Override
    public UserDto createUser(UserCreationDto userCreationDto) {
        User user = UserDtoMapper.toUser(userCreationDto);
        user.updateDailyCaloriesNorm();

        try {
            User saved = userRepository.save(user);
            return UserDtoMapper.toUserDto(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email адрес уже зарегистрирован: " + userCreationDto.getEmail());
        }
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> all = userRepository.findAll();
        return UserDtoMapper.toUserDto(all);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователя с id " + id + "не существует"));
        return UserDtoMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Пользователь с id: " + id + " не найден.");
        }
        userRepository.deleteById(id);
    }
}
