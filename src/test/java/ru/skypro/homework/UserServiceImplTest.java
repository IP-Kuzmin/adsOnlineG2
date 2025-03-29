
package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.ChangeAndNewPassword;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.exceptions.UserWrongPasswordResponseException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.ImageServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private UserServiceImpl userService;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private ImageServiceImpl imageService;

    private final UserModel adminUser = new UserModel(1L,"admin@example.com","Oleg","Olegov",
            "+7 (777) 777-77-77", Role.ADMIN,"","");
    private final UserModel easyUser = new UserModel(1L,"user@example.com","Anna","Olegov",
            "+7 (777) 777-77-77", Role.USER,"","");


    @TempDir
    Path tempDir;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        passwordEncoder = mock(PasswordEncoder.class);
        imageService = mock(ImageServiceImpl.class);
        userService = new UserServiceImpl(userRepository, userMapper, passwordEncoder, imageService );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken("user@example.com", null));
        SecurityContextHolder.setContext(context);
    }

    @Test
    void getCurrentUser_found_returnsUser() {
        UserModel userModel = new UserModel();
        userModel.setEmail("user@example.com");
        User userDto = new User();
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(userModel));
        when(userMapper.toDto(userModel)).thenReturn(userDto);

        User result = userService.getCurrentUser();
        assertEquals(userDto, result);
    }

    @Test
    void updateUser_updatesFields() {
        UpdateUser dto = new UpdateUser("Имя", "Фамилия", "+79991234567");
        UserModel userModel = new UserModel();
        userModel.setEmail("user@example.com");
        userModel.setId(1L);
        userModel.setRole(Role.ADMIN);
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(userModel));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userModel));
        when(userMapper.toDto(userModel)).thenReturn(new User());

        User updated = userService.updateUser(dto);

        assertEquals(dto.getFirstName(), userModel.getFirstName());
        assertEquals(dto.getLastName(), userModel.getLastName());
        assertEquals(dto.getPhone(), userModel.getPhone());
    }

    @Test
    void setPassword_shouldUpdate_whenOldCorrect() {
        ChangeAndNewPassword dto = new ChangeAndNewPassword("oldPass", "newPass");
        UserModel userModel = new UserModel();
        userModel.setEmail("user@example.com");
        userModel.setPassword("encodedOldPass");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(userModel));
        when(passwordEncoder.matches("oldPass", "encodedOldPass")).thenReturn(true);
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");
        when(userRepository.save(any())).thenReturn(userModel);

        assertDoesNotThrow(() -> userService.setPassword(dto));
        assertEquals("encodedNewPass", userModel.getPassword());
    }

    @Test
    void setPassword_shouldThrow_whenOldWrong() {
        ChangeAndNewPassword dto = new ChangeAndNewPassword("wrongPass", "newPass");
        UserModel userModel = new UserModel();
        userModel.setEmail("user@example.com");
        userModel.setPassword("encodedPass");
        userModel.setId(1L);
        userModel.setRole(Role.ADMIN);
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(userModel));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userModel));

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(userModel));
        when(passwordEncoder.matches("wrongPass", "encodedPass")).thenReturn(false);

        assertThrows(UserWrongPasswordResponseException.class, () -> userService.setPassword(dto));
    }
}
