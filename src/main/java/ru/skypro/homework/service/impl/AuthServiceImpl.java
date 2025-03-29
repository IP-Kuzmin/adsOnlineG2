package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.exceptions.UserNotFoundResponseException;
import ru.skypro.homework.exceptions.UserPasswordNeededResponseException;
import ru.skypro.homework.exceptions.UserWrongPasswordResponseException;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(Register register) {
        if (register.getUsername() == null || register.getPassword() == null) {
            log.warn("Регистрация отклонена: логин или пароль отсутствуют");
            throw new UserPasswordNeededResponseException();
        }
        UserModel user = new UserModel();
        user.setEmail(register.getUsername());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setPhone(register.getPhone());
        user.setRole(register.getRole());
        userRepository.save(user);
        log.info("Пользователь {} успешно зарегистрирован", user.getEmail());
    }

    @Override
    public void loginUser(Login login) {
        log.debug("Попытка входа пользователя: {}", login.getUsername());
        UserModel user = userRepository.findByEmail(login.getUsername())
                .orElseThrow(() -> {
                    log.error("Пользователь {} не найден", login.getUsername());
                    return new UserNotFoundResponseException();
                });

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            log.warn("Неверный пароль для пользователя {}", login.getUsername());
            throw new UserWrongPasswordResponseException();
        }

        log.info("Пользователь {} успешно вошел в систему", login.getUsername());
    }
}
