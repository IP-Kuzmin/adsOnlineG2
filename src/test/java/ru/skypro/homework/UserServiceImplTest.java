package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private UserServiceImpl userService;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, userMapper, passwordEncoder); // конструктор с encoder

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
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(userModel));
        when(userMapper.toDto(userModel)).thenReturn(new User());

        User updated = userService.updateUser(dto);

        assertEquals(dto.getFirstName(), userModel.getFirstName());
        assertEquals(dto.getLastName(), userModel.getLastName());
        assertEquals(dto.getPhone(), userModel.getPhone());
    }

    @Test
    void updateUserImage_setsImagePath() {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("avatar.png");

        UserModel user = new UserModel();
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        userService.updateUserImage(mockFile);

        assertEquals("/images/users/avatar.png", user.getImage());
        verify(userRepository).save(user);
    }
}
