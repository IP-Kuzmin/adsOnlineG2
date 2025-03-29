package ru.skypro.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {

    private AuthServiceImpl authService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authService = new AuthServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void registerUser_validData_savesUser() {
        Register register = new Register("user@example.com", "pass", "Name", "Last", "+79991234567", ru.skypro.homework.dto.Role.USER);

        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");

        authService.registerUser(register);

        verify(userRepository, times(1)).save(any(UserModel.class));
    }

    @Test
    void loginUser_validCredentials_success() {
        Login login = new Login("user@example.com", "pass");
        UserModel user = new UserModel();
        user.setEmail("user@example.com");
        user.setPassword("encoded");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass", "encoded")).thenReturn(true);

        assertDoesNotThrow(() -> authService.loginUser(login));
    }

    @Test
    void loginUser_wrongPassword_throws() {
        Login login = new Login("user@example.com", "wrong");
        UserModel user = new UserModel();
        user.setEmail("user@example.com");
        user.setPassword("encoded");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> authService.loginUser(login));
    }
}
