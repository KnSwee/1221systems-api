package user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.myspar.controller.UserController;
import ru.myspar.dto.user.UserCreationDto;
import ru.myspar.dto.user.UserDto;
import ru.myspar.enums.Gender;
import ru.myspar.enums.Goal;
import ru.myspar.service.user.UserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserCreationDto userCreationDto;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userCreationDto = new UserCreationDto("John Doe", "john.doe@example.com", 30, 75.0, 180.0, Gender.MALE, Goal.MAINTENANCE);
        userDto = new UserDto(1, "John Doe", "john.doe@example.com", 30, 75.0, 180.0, "Male", "Maintenance", 2000);
    }

    @Test
    void createUserTest() {
        when(userService.createUser(userCreationDto)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.createUser(userCreationDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDto, response.getBody());
        verify(userService, times(1)).createUser(userCreationDto);
    }

    @Test
    void getUsersTest() {
        UserDto user2 = new UserDto(2, "Jane Doe", "jane.doe@example.com", 25, 60.0, 165.0, "Female", "Похудение", 1500);
        List<UserDto> expectedUsers = Arrays.asList(userDto, user2);

        when(userService.getUsers()).thenReturn(expectedUsers);

        ResponseEntity<List<UserDto>> response = userController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedUsers, response.getBody());
        verify(userService, times(1)).getUsers();
    }

    @Test
    void getUserTest() {
        int userId = 1;
        when(userService.getUserById(userId)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDto, response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void deleteUserTest() {
        int userId = 1;

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }
}
