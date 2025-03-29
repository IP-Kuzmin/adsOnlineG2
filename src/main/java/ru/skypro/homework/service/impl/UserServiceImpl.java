package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exceptions.ImageAvatarSaveException;
import ru.skypro.homework.exceptions.UserNotFoundResponseException;
import ru.skypro.homework.exceptions.UserWrongPasswordResponseException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.upload.dir}")
    private String imageDir;

    @Value("${app.upload.absolute-path:true}")
    private boolean isUseAbsolutePath;

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        log.debug("Получен текущий пользователь из контекста: {}", username);
        return username;
    }

    @Override
    public void setPassword(ChangeAndNewPassword passwords) {
        UserModel user = userRepository.findByEmail(getCurrentUsername())
                .orElseThrow(UserNotFoundResponseException::new);

        if (!passwordEncoder.matches(passwords.getCurrentPassword(), user.getPassword())) {
            log.warn("⛔Неверный текущий пароль для {}", user.getEmail());
            throw new UserWrongPasswordResponseException();
        }

        String newHash = passwordEncoder.encode(passwords.getNewPassword());
        user.setPassword(newHash);
        UserModel savedUser = userRepository.save(user);
        log.debug("Информация о пользователе {} изменена",savedUser.getId());
    }

    @Override
    public User getCurrentUser() {
        String email = getCurrentUsername();
        return userMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundResponseException::new));
    }

    @Override
    public User updateUser(UpdateUser dto) {
        UserModel user = userRepository.findByEmail(getCurrentUsername())
                .orElseThrow(() -> {
                    log.error("Пользователь для обновления не найден");
                    return new UserNotFoundResponseException();
                });

        log.debug("Данные до обновления: {}", user);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());

        log.info("Пользователь {} обновил профиль", user.getEmail());
        log.debug("Обновлённые поля: firstName={}, lastName={}, phone={}",
                dto.getFirstName(), dto.getLastName(), dto.getPhone());

        UserModel savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    @Override
    public void updateUserImage(MultipartFile image) {
        UserModel user = userRepository.findByEmail(getCurrentUsername())
                .orElseThrow(UserNotFoundResponseException::new);

        String contentType = image.getContentType();
        String extension = getExtensionFromContentType(contentType);
        String filename = user.getId() + "." + extension;

        String absolutePath = "";
        if (!isUseAbsolutePath) {
            absolutePath = System.getProperty("user.dir");
        }
        Path savePath = Paths.get(absolutePath, imageDir.concat("/images/avatar/"+ filename));
        try {
            Files.createDirectories(savePath.getParent());
            image.transferTo(savePath.toFile());
        } catch (IOException e) {
            throw new ImageAvatarSaveException(e.getMessage());
        }

        user.setImage("/".concat(imageDir.concat("/images/avatar/" +filename)));

        UserModel savedUser = userRepository.save(user);
        log.debug("Картинка обновлена для пользователя {}, путь картинки = {}", savedUser.getId(), savedUser.getImage());
    }

    private String getExtensionFromContentType(String contentType) {
        if (contentType == null) return "jpg";
        return switch (contentType) {
            case "image/png" -> "png";
            case "image/gif" -> "gif";
            case "image/webp" -> "webp";
            default -> "jpg";
        };
    }
}
