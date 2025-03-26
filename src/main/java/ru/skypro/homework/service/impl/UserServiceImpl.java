package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void setPassword(ChangeAndNewPassword newPassword) {
    }

    @Override
    public User getCurrentUser() {
        return new User(
                1,
                "user@example.com",
                "John",
                "Doe",
                "+7234567890",
                Role.USER,
                "https://example.com/image.jpg"
        );
    }

    @Override
    public User updateUser(UpdateUser updateUser) {
        return new User(
                1,
                "user@example.com",
                updateUser.getFirstName(),
                updateUser.getLastName(),
                updateUser.getPhone(),
                Role.USER,
                "https://example.com/image.jpg"
        );
    }

    @Override
    public void updateUserImage(AvatarImage imageBody) {
    }
}
