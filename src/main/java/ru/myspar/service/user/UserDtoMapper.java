package ru.myspar.service.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.myspar.dto.user.UserCreationDto;
import ru.myspar.dto.user.UserDto;
import ru.myspar.model.User;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDtoMapper {

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setAge(user.getAge());
        userDto.setHeight(user.getHeight());
        userDto.setWeight(user.getWeight());
        userDto.setGender(user.getGender().toString());
        userDto.setGoal(user.getGoal().toString());
        userDto.setDailyCaloriesNorm(user.getDailyCaloriesNorm());
        return userDto;
    }

    public static List<UserDto> toUserDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(toUserDto(user));
        }
        return userDtos;
    }

    public static User toUser(UserCreationDto userCreationDto) {
        User user = new User();
        user.setName(userCreationDto.getName());
        user.setEmail(userCreationDto.getEmail());
        user.setAge(userCreationDto.getAge());
        user.setHeight(userCreationDto.getHeight());
        user.setWeight(userCreationDto.getWeight());
        user.setGender(userCreationDto.getGender());
        user.setGoal(userCreationDto.getGoal());
        return user;
    }
}
