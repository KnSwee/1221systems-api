package ru.myspar.service.user;

import jakarta.validation.Valid;
import ru.myspar.dto.user.UserCreationDto;
import ru.myspar.dto.user.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(@Valid UserCreationDto userCreationDto);

    List<UserDto> getUsers();

    UserDto getUserById(Integer id);

    boolean deleteUser(Integer id);
}
