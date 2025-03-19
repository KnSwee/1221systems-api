package ru.myspar.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myspar.dto.user.UserCreationDto;
import ru.myspar.dto.user.UserDto;
import ru.myspar.exceptions.NotFoundException;
import ru.myspar.model.User;
import ru.myspar.repository.DishRepository;
import ru.myspar.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;

    @Override
    public UserDto createUser(UserCreationDto userCreationDto) {
        User user = userRepository.save(UserDtoMapper.toUser(userCreationDto));
        return UserDtoMapper.toUserDto(user);
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
    public boolean deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }
}
