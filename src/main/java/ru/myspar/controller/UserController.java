package ru.myspar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.myspar.dto.user.UserCreationDto;
import ru.myspar.dto.user.UserDto;
import ru.myspar.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserCreationDto userCreationDto) {
        return userService.createUser(userCreationDto);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping
    public boolean deleteUser(@RequestParam int id) {
        return userService.deleteUser(id);
    }

}
