package user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.myspar.dto.user.UserCreationDto;
import ru.myspar.dto.user.UserDto;
import ru.myspar.enums.Gender;
import ru.myspar.enums.Goal;
import ru.myspar.exception.NotFoundException;
import ru.myspar.model.User;
import ru.myspar.repository.UserRepository;
import ru.myspar.service.user.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserCreationDto userCreationDto;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userCreationDto = new UserCreationDto("John Doe", "john.doe@example.com", 30, 75.0, 180.0, Gender.MALE, Goal.MAINTENANCE);

        user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setAge(30);
        user.setWeight(75.0);
        user.setHeight(180.0);
        user.setGender(Gender.MALE);
        user.setGoal(Goal.MAINTENANCE);

        userDto = new UserDto(1, "John Doe", "john.doe@example.com", 30, 75.0, 180.0, Gender.MALE.toString(), Goal.MAINTENANCE.toString(), user.calculateDailyCaloriesNorm());  // Adjust calorie value accordingly
    }

    @Test
    void createUserTest() {
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User userToSave = invocation.getArgument(0);
            userToSave.setId(1);
            userToSave.updateDailyCaloriesNorm();
            return userToSave;
        });

        UserDto createdUser = userService.createUser(userCreationDto);

        assertNotNull(createdUser);
        assertEquals(userDto, createdUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUsersTest() {
        User user2 = new User();
        user2.setId(2);
        user2.setName("Jane Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setAge(25);
        user2.setWeight(60.0);
        user2.setHeight(165.0);
        user2.setGender(Gender.FEMALE);
        user2.setGoal(Goal.WEIGHT_LOSS);
        user2.updateDailyCaloriesNorm();

        List<User> users = Arrays.asList(user, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> userDtos = userService.getUsers();

        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserByIdTest() {
        int userId = 1;
        user.updateDailyCaloriesNorm();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto userDtoResult = userService.getUserById(userId);

        assertNotNull(userDtoResult);
        assertEquals(userDto, userDtoResult);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserByIdNonExistingUserTest() {
        int userId = 100;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void deleteUserTest() {
        int userId = 1;
        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUserNonExistingUserTest() {
        int userId = 100;
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.deleteUser(userId));
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(anyInt());
    }
}
