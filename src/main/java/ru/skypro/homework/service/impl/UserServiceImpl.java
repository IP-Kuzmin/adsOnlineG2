package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exceptions.ExceptionsMessage;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        log.debug("Получен текущий пользователь из контекста: {}", username);
        return username;
    }

    @Override
    public void setPassword(ChangeAndNewPassword passwords) {
        UserModel user = userRepository.findByEmail(getCurrentUsername())
                .orElseThrow(() -> {
                    log.error("Ошибка при смене пароля: пользователь не найден");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionsMessage.USER_NOT_FOUND.getMessage());
                });

        if (!"password".equals(passwords.getCurrentPassword())) {
            log.warn("Попытка смены пароля с неверным текущим паролем для пользователя {}", user.getEmail());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ExceptionsMessage.WRONG_PASSWORD.getMessage());
        }

        log.info("Пользователь {} успешно сменил пароль", user.getEmail());
        log.debug("Новый пароль: {}", passwords.getNewPassword());
    }

    @Override
    public User getCurrentUser() {
        System.out.println("jjjj");
        String email = getCurrentUsername();
        log.debug("Поиск пользователя по email: {}", email);
        return userMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Пользователь с email {} не найден", email);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionsMessage.USER_NOT_FOUND.getMessage());
                }));
    }

    @Override
    public User updateUser(UpdateUser dto) {
        UserModel user = userRepository.findByEmail(getCurrentUsername())
                .orElseThrow(() -> {
                    log.error("Пользователь для обновления не найден");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionsMessage.USER_NOT_FOUND.getMessage());
                });

        log.debug("Данные до обновления: {}", user);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());

        log.info("Пользователь {} обновил профиль", user.getEmail());
        log.debug("Обновлённые поля: firstName={}, lastName={}, phone={}",
                dto.getFirstName(), dto.getLastName(), dto.getPhone());

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void updateUserImage(MultipartFile image) {
        UserModel user = userRepository.findByEmail(getCurrentUsername())
                .orElseThrow(() -> {
                    log.error("Пользователь для обновления аватара не найден");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, ExceptionsMessage.USER_NOT_FOUND.getMessage());
                });

        String imagePath = "/images/users/" + image.getOriginalFilename();
        user.setImage(imagePath);
        userRepository.save(user);

        log.info("Пользователь {} обновил аватар", user.getEmail());
        log.debug("Путь до нового изображения: {}", imagePath);
    }
}
