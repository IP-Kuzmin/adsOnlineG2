package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exceptions.PermissionDeniedUpdateResponseException;
import ru.skypro.homework.exceptions.UserNotFoundResponseException;
import ru.skypro.homework.exceptions.UserWrongPasswordResponseException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.models.UserModel;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.enums.ImageSaveType;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

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
            log.debug("⛔Неверный текущий пароль для {}", user.getEmail());
            throw new UserWrongPasswordResponseException();
        }

        String newHash = passwordEncoder.encode(passwords.getNewPassword());
        user.setPassword(newHash);
        UserModel savedUser = userRepository.save(user);
        log.debug("Информация о пользователе {} изменена", savedUser.getId());
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

        log.debug("Обновление профиля: firstName={}, lastName={}, phone={}",
                dto.getFirstName(), dto.getLastName(), dto.getPhone());

        if (isOwner(user.getId())) {
            UserModel savedUser = userRepository.save(user);
            return userMapper.toDto(savedUser);
        } else {
            throw new PermissionDeniedUpdateResponseException("User update Denied");
        }
    }

    @Override
    public void updateUserImage(MultipartFile image) {
        UserModel user = userRepository.findByEmail(getCurrentUsername())
                .orElseThrow(UserNotFoundResponseException::new);

        if (!isOwner(user.getId())) {
            throw new PermissionDeniedUpdateResponseException("User image update Denied");
        }

        String imagePath = imageService.saveImage(image, user.getId(), ImageSaveType.AVATAR);
        user.setImage(imagePath);

        UserModel savedUser = userRepository.save(user);
        log.debug("Картинка обновлена для пользователя {}, путь картинки = {}", savedUser.getId(), savedUser.getImage());
    }

    private boolean isOwner(Long id) {
        UserModel user = userRepository.findById(id).orElseThrow(UserNotFoundResponseException::new);
        return getCurrentUser().getRole() == Role.ADMIN || user.getEmail().equals(getCurrentUsername());
    }
}